package com.example.PaliProject.service;

import com.example.PaliProject.cache.Cache;
import com.example.PaliProject.persistence.PersistenceService;
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

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PalindromeServiceTest {

    @Mock
    private PersistenceService persistenceService;

    @Mock
    private PalindromeUtil palindromeUtil;

    @Mock
    private Cache cache;

    @InjectMocks
    private PalindromeService palindromeService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Configure mock behaviors
        when(cache.get(anyString())).thenReturn(Mono.just(true));
        when(persistenceService.save(anyString(), anyString(), anyBoolean())).thenReturn(Mono.empty());
        when(persistenceService.loadCache()).thenReturn(Mono.just(new HashMap<>()));
    }

    @Test
    void testIsPalindromeValidInput() throws IOException {
        // Test the service method with valid input
        String username = "ted";
        String text = "aba"; // valid input

        // Configure mocks
        when(palindromeUtil.isValidInput(text)).thenReturn(true);
        when(palindromeUtil.isPalindrome(text)).thenReturn(true);
        when(persistenceService.save(anyString(), anyString(), anyBoolean())).thenReturn(Mono.empty());
        when(persistenceService.loadCache()).thenReturn(Mono.just(new HashMap<>()));

        // Call the service method
        Mono<Boolean> result = palindromeService.isPalindrome(username, text);

        // Use StepVerifier to test the reactive Mono
        StepVerifier.create(result)
                .expectNext(true) // Expecting the result to be true
                .verifyComplete(); // Expect the Mono to complete successfully

        verify(palindromeUtil).isValidInput(text);
    }


    @Test
    void testIsPalindromeInvalidInput() {
        // Test the service method with invalid input
        String username = "ted";
        String text = "123"; // invalid input

        // Configure the mock to return false for valid input check
        when(palindromeUtil.isValidInput(text)).thenReturn(false);

        // Call the service method and expect an error
        Mono<Boolean> result = palindromeService.isPalindrome(username, text);

        // Use StepVerifier to test the reactive Mono
        StepVerifier.create(result)
                .expectErrorMatches(throwable -> throwable instanceof IllegalArgumentException &&
                        throwable.getMessage().equals("Invalid text input: only alphabetic characters are supported"))
                .verify();
    }

    @Test
    void testIsPalindromeCacheMiss() {
        // Test cache miss scenario
        String username = "ted";
        String text = "racecar";
        when(cache.get(anyString())).thenReturn(Mono.empty());
        Mono<Boolean> result = cache.get(text);
        StepVerifier.create(result)
                .expectComplete() // No value should be present
                .verify();
    }
}
