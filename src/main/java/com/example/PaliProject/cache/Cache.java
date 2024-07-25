package com.example.PaliProject.cache;

import reactor.core.publisher.Mono;

/**
 * Interface for a cache system that supports asynchronous operations.
 * <p>
 * This interface defines methods for retrieving, adding, and removing values in a cache.
 * The operations are asynchronous and return {@link Mono} to allow for non-blocking access.
 * </p>
 */
public interface Cache {
    /**
     * Retrieves a value from the cache.
     * <p>
     * This method returns a {@link Mono} that emits the cached value if it exists,
     * or an empty {@link Mono} if the value is not found.
     * </p>
     *
     * @param key the key associated with the cached value
     * @return a {@link Mono} emitting the cached value or empty if not found
     */
    Mono<Boolean> get(String key);

    /**
     * Adds a value to the cache.
     * <p>
     * This method updates the cache with the specified key-value pair.
     * It returns a {@link Mono} that completes when the value has been successfully added to the cache.
     * </p>
     *
     * @param key   the key associated with the value to be cached
     * @param value the value to be cached
     * @return a {@link Mono} that completes when the value has been successfully added to the cache
     */
    Mono<Void> put(String key, Boolean value);

    /**
     * Removes a value from the cache.
     * <p>
     * This method removes the value associated with the specified key from the cache.
     * It returns a {@link Mono} that completes when the value has been successfully removed from the cache.
     * </p>
     *
     * @param key the key associated with the value to be removed
     * @return a {@link Mono} that completes when the value has been successfully removed from the cache
     */
    Mono<Void> remove(String key);
}
