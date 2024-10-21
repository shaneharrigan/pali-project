package com.example.PaliProject.service;

import com.example.PaliProject.cache.MyCache;
import com.example.PaliProject.persistence.PersistenceService;
import com.example.PaliProject.util.PalindromeUtil;
import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;
import reactor.core.publisher.Flux;
import java.io.IOException;

/**
 * Service for handling palindrome-related operations.
 * <p>
 * This service provides functionality to check if a given text is a palindrome. It also manages caching of
 * palindrome results and persists them using a {@link PersistenceService}.
 * </p>
 */
@Service
public class PalindromeService {
    private static final Logger logger = LoggerFactory.getLogger(PalindromeService.class);

    private final PalindromeUtil palindromeUtil;
    private final PersistenceService persistenceService;
    private final MyCache cache;

    /**
     * Constructs a {@link PalindromeService} with the specified {@link PalindromeUtil}, {@link PersistenceService}, and {@link MyCache}.
     *
     * @param palindromeUtil    utility for palindrome checks and input validation
     * @param persistenceService service for persisting palindrome results
     * @param cache             caching mechanism for storing palindrome results
     */
    public PalindromeService(PalindromeUtil palindromeUtil, PersistenceService persistenceService, MyCache cache) {
        this.palindromeUtil = palindromeUtil;
        this.persistenceService = persistenceService;
        this.cache = cache;
    }

    /**
     * Initializes the service by loading cached palindrome data from the {@link PersistenceService}.
     * <p>
     * This method retrieves cached data and populates the internal cache with it. It may throw an
     * {@link IOException} if there are issues with loading the cache.
     * </p>
     *
     * @throws IOException if an error occurs while loading the cache
     */
    @PostConstruct
    public void initialize() throws IOException {
        logger.info("Initializing PalindromeService by loading cached data.");
        try {
            this.persistenceService.loadCache()
                    .flatMapMany(map -> Flux.fromIterable(map.entrySet())) // Use Flux.fromIterable here
                    .flatMap(entry -> cache.put(entry.getKey(), entry.getValue()))
                    .doOnError(error -> logger.error("Error initializing cache: {}", error.getMessage()))
                    .subscribe();
            logger.info("Initialization complete.");
        } catch (IOException e) {
            logger.error("Error during initialization", e);
            throw e;
        }
    }

    /**
     * Checks if the given text is a palindrome.
     * <p>
     * This method first checks the cache for the result. If the result is not cached, it validates the input,
     * processes the palindrome check, updates the cache, and persists the result.
     * </p>
     *
     * @param username the username associated with the palindrome check request
     * @param text     the text to be checked
     * @return a {@link Mono} emitting {@code true} if the text is a palindrome, {@code false} otherwise
     *         or an error if the input is invalid
     */
    public Mono<Boolean> isPalindrome(String username, String text) {
        logger.debug("Checking if '{}' is a palindrome for user '{}'", text, username);

        // Validate input
        if (!palindromeUtil.isValidInput(text)) {
            return Mono.error(new IllegalArgumentException("Invalid text input: only alphabetic characters are supported"));
        }

        // Attempt to retrieve the value from cache
        return cache.get(text)
                .doOnSubscribe(subscription -> logger.debug("Subscribed to cache retrieval for text '{}'", text))
                .switchIfEmpty(Mono.defer(() -> {
                    // Cache miss, proceed to process and cache the result
                    logger.debug("Cache miss for text '{}'. Processing...", text);
                    return processAndCachePalindrome(username, text);
                }))
                .doOnSuccess(result -> logger.debug("Result for text '{}': {}", text, result))
                .doOnError(error -> logger.error("Error during palindrome check for text '{}': {}", text, error.getMessage()));
    }

    /**
     * Processes the palindrome check for the given text and updates the cache and persistence layer.
     * <p>
     * This method validates the input, checks if the text is a palindrome, updates the cache with the result,
     * and persists the result using {@link PersistenceService}. It logs appropriate messages for each operation.
     * </p>
     *
     * @param username the username associated with the palindrome check request
     * @param text     the text to be processed
     * @return a {@link Mono} emitting {@code true} if the text is a palindrome, {@code false} otherwise
     */
    private Mono<Boolean> processAndCachePalindrome(String username, String text) {

        boolean isPalindrome = palindromeUtil.isPalindrome(text);

        return cache.put(text, isPalindrome)
                .then(persistenceService.save(username, text, isPalindrome))
                .then(Mono.just(isPalindrome));
    }
}
