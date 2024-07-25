package com.example.PaliProject.service;

import com.example.PaliProject.cache.ICache;
import com.example.PaliProject.persistence.IPersistenceService;
import com.example.PaliProject.util.PalindromeUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.io.IOException;
import java.util.HashMap;

import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PalindromeServiceTest {

    @Mock
    private IPersistenceService IPersistenceService;

    @Mock
    private PalindromeUtil palindromeUtil;

    @Mock
    private ICache ICache;

    @InjectMocks
    private PalindromeService palindromeService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Configure mock behaviors
        when(ICache.get(anyString())).thenReturn(Mono.just(true));
        when(IPersistenceService.save(anyString(), anyString(), anyBoolean())).thenReturn(Mono.empty());
        when(IPersistenceService.loadCache()).thenReturn(Mono.just(new HashMap<>()));
    }

    @Test
    void testIsPalindromeValidInput() throws IOException {
        String username = "ted";
        String text = "aba";

        when(palindromeUtil.isValidInput(text)).thenReturn(true);
        when(palindromeUtil.isPalindrome(text)).thenReturn(true);
        when(IPersistenceService.save(anyString(), anyString(), anyBoolean())).thenReturn(Mono.empty());
        when(IPersistenceService.loadCache()).thenReturn(Mono.just(new HashMap<>()));

        Mono<Boolean> result = palindromeService.isPalindrome(username, text);

        StepVerifier.create(result)
                .expectNext(true)
                .verifyComplete();

        verify(palindromeUtil).isValidInput(text);
    }


    @Test
    void testIsPalindromeInvalidInput() {
        String username = "ted";
        String text = "123";

        when(palindromeUtil.isValidInput(text)).thenReturn(false);

        Mono<Boolean> result = palindromeService.isPalindrome(username, text);

        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Invalid text input: only alphabetic characters are supported"))
                .verify();
    }

    @Test
    void testIsPalindromeCacheMiss() {
        String text = "racecar";
        when(ICache.get(anyString())).thenReturn(Mono.empty());
        Mono<Boolean> result = ICache.get(text);
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }
}
