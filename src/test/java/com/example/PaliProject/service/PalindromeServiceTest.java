package com.example.PaliProject.service;

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

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyBoolean;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

class PalindromeServiceTest {

    @Mock
    private PersistenceService persistenceService;

    @Mock
    private PalindromeUtil palindromeUtil;

    @InjectMocks
    private PalindromeService palindromeService;

    @BeforeEach
    void setUp() throws IOException {
        MockitoAnnotations.openMocks(this);

        // Configure mock behaviors
        when(palindromeUtil.isValidInput(anyString())).thenReturn(true);
        when(palindromeUtil.isPalindrome(anyString())).thenReturn(true);
        when(persistenceService.save(anyString(), anyString(), anyBoolean())).thenReturn(Mono.empty());
        when(persistenceService.loadCache()).thenReturn(Mono.just(new HashMap<>()));
    }

    @Test
    void testIsPalindromeValidInput() {
        // Test the service method with valid input
        String username = "ted";
        String text = "aba";

        // Define behavior for the palindrome check
        when(palindromeUtil.isPalindrome(text)).thenReturn(true);

        // Call the service method
        palindromeService.isPalindrome(username, text)
                .doOnNext(result -> {
                    // Assert the result
                    assertTrue(result);
                    // Verify interactions with mocks
                    verify(palindromeUtil).isValidInput(text);
                    verify(palindromeUtil).isPalindrome(text);
                    verify(persistenceService).save(username, text, true);
                })
                .block(); // To force execution and wait for the result
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

        // Configure the mock for palindrome check
        when(palindromeUtil.isPalindrome(text)).thenReturn(true);

        // Call the service method
        palindromeService.isPalindrome(username, text)
                .doOnNext(result -> {
                    // Assert the result
                    assertTrue(result);
                    // Verify interactions with mocks
                    verify(palindromeUtil).isValidInput(text);
                    verify(palindromeUtil).isPalindrome(text);
                    verify(persistenceService).save(username, text, true);
                })
                .block(); // To force execution and wait for the result
    }
}
