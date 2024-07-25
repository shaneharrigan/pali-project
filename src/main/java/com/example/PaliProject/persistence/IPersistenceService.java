package com.example.PaliProject.persistence;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

/**
 * Interface for persistence services related to storing and retrieving data.
 * <p>
 * This interface defines the contract for services that handle the saving and loading of data,
 * typically involving some form of persistence mechanism such as a file or database.
 * </p>
 */
public interface IPersistenceService {

    /**
     * Saves the provided data into the persistence store.
     * <p>
     * This method is intended to store the given text along with associated metadata (username and
     * whether the text is a palindrome) into the persistence store. The operation is asynchronous.
     * </p>
     *
     * @param username     the username associated with the text to be saved
     * @param text         the text to be saved
     * @param isPalindrome {@code true} if the text is a palindrome, {@code false} otherwise
     * @return a {@link Mono} that completes when the data has been successfully saved
     */
    Mono<Void> save(String username, String text, boolean isPalindrome);

    /**
     * Loads the cached data from the persistence store.
     * <p>
     * This method retrieves the cached data from the persistence store and returns it as a map,
     * where the key is the text and the value is a boolean indicating if the text is a palindrome.
     * The operation is asynchronous.
     * </p>
     *
     * @return a {@link Mono} containing a {@link Map} of the cached data, where the key is the text and
     *         the value is a boolean indicating if the text is a palindrome
     * @throws IOException if an I/O error occurs while loading the data
     */
    Mono<Map<String, Boolean>> loadCache() throws IOException;
}
