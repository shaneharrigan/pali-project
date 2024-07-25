package com.example.PaliProject.cache;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * In-memory implementation of {@link ICache} that uses a {@link ConcurrentMap} for storing cached values.
 * <p>
 * This class provides methods to get, put, and remove values from the cache. It uses a {@link ConcurrentHashMap}
 * to ensure thread-safe operations.
 * </p>
 */
@Component
public class InMemoryICacheImpl implements ICache {
    private static final Logger logger = LoggerFactory.getLogger(InMemoryICacheImpl.class);

    private final ConcurrentMap<String, Boolean> cache = new ConcurrentHashMap<>();

    /**
     * Retrieves a value from the cache.
     * <p>
     * This method returns a {@link Mono} that emits the cached value if it exists, or an empty {@link Mono}
     * if the value is not found.
     * </p>
     *
     * @param key the key associated with the cached value
     * @return a {@link Mono} emitting the cached value or empty if not found
     */
    @Override
    public Mono<Boolean> get(String key) {
        Boolean value = cache.get(key);
        if (value != null) {
            logger.debug("Cache hit for key '{}'", key);
        } else {
            logger.debug("Cache miss for key '{}'", key);
        }
        return Mono.justOrEmpty(value);
    }

    /**
     * Adds a value to the cache.
     * <p>
     * This method updates the cache with the specified key-value pair. It logs the action at the info level.
     * </p>
     *
     * @param key   the key associated with the value to be cached
     * @param value the value to be cached
     * @return a {@link Mono} that completes when the value has been successfully added to the cache
     */
    @Override
    public Mono<Void> put(String key, Boolean value) {
        cache.put(key, value);
        logger.info("Cached value for key '{}': {}", key, value);
        return Mono.empty();
    }

    /**
     * Removes a value from the cache.
     * <p>
     * This method removes the value associated with the specified key from the cache. It logs the action at the info level.
     * </p>
     *
     * @param key the key associated with the value to be removed
     * @return a {@link Mono} that completes when the value has been successfully removed from the cache
     */
    @Override
    public Mono<Void> remove(String key) {
        Boolean removedValue = cache.remove(key);
        if (removedValue != null) {
            logger.info("Removed cached value for key '{}': {}", key, removedValue);
        } else {
            logger.debug("No value found to remove for key '{}'", key);
        }
        return Mono.empty();
    }
}
