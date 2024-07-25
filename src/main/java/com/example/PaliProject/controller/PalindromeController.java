package com.example.PaliProject.controller;

import com.example.PaliProject.service.PalindromeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

/**
 * REST controller for handling palindrome-related requests.
 * <p>
 * This controller exposes an endpoint to check if a given text is a palindrome and associates it with a username.
 * It relies on the {@link PalindromeService} for the actual palindrome checking and data persistence.
 * </p>
 */
@RestController
@RequestMapping("/api/palindrome")
public class PalindromeController {

    private static final Logger logger = LoggerFactory.getLogger(PalindromeController.class);

    private final PalindromeService palindromeService;

    /**
     * Constructs a {@link PalindromeController} with the specified {@link PalindromeService}.
     * <p>
     * The constructor initializes the service by calling its {@link PalindromeService#initialize()} method.
     * </p>
     *
     * @param palindromeService the service used for palindrome checking and persistence
     * @throws IOException if an error occurs during the initialization of the {@code palindromeService}
     */
    @Autowired
    public PalindromeController(PalindromeService palindromeService) throws IOException {
        this.palindromeService = palindromeService;
        try {
            this.palindromeService.initialize();
            logger.info("PalindromeService initialized successfully.");
        } catch (IOException e) {
            logger.error("Error initializing PalindromeService", e);
            throw e;
        }
    }

    /**
     * Endpoint for checking if a given text is a palindrome.
     * <p>
     * This method receives a username and text as request parameters and delegates the palindrome check to
     * {@link PalindromeService#isPalindrome(String, String)}.
     * </p>
     *
     * @param username the username associated with the text to be checked
     * @param text     the text to be checked for palindrome property
     * @return a {@link Mono} that emits {@code true} if the text is a palindrome, or {@code false} otherwise
     */
    @PostMapping
    public Mono<Boolean> checkPalindrome(@RequestParam String username, @RequestParam String text) {
        logger.info("Received palindrome check request: username={}, text={}", username, text);

        return palindromeService.isPalindrome(username, text)
                .doOnSuccess(isPalindrome -> logger.info("Palindrome check result: username={}, text={}, isPalindrome={}", username, text, isPalindrome))
                .doOnError(error -> logger.error("Error checking palindrome for username={}, text={}", username, text, error));
    }
}
