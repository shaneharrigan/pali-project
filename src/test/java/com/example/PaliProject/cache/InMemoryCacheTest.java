package com.example.PaliProject.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

class InMemoryCacheTest {

    private InMemoryCache cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cache = new InMemoryCache();
    }

    @Test
    void testPut() {
        // Define the key and value to be cached
        String key = "testKey";
        Boolean value = true;

        // Call the put method
        cache.put(key, value)
                .as(StepVerifier::create)
                .expectNextCount(0) // No value expected as Mono<Void> is empty
                .verifyComplete();

        // Verify that the value was cached
        Mono<Boolean> result = cache.get(key);
        StepVerifier.create(result)
                .expectNext(value)
                .verifyComplete();
    }

    @Test
    void testGetCacheHit() {
        // Define the key and value to be cached
        String key = "testKey";
        Boolean value = true;
        cache.put(key, value).block(); // Ensure value is put in cache

        // Call the get method
        Mono<Boolean> result = cache.get(key);
        StepVerifier.create(result)
                .expectNext(value)
                .verifyComplete();
    }

    @Test
    void testGetCacheMiss() {
        // Define the key that does not exist in the cache
        String key = "nonExistentKey";

        // Call the get method
        Mono<Boolean> result = cache.get(key);
        StepVerifier.create(result)
                .expectComplete() // No value should be present
                .verify();
    }

    @Test
    void testRemove() {
        // Define the key and value to be cached
        String key = "testKey";
        Boolean value = true;
        cache.put(key, value).block(); // Ensure value is put in cache

        // Call the remove method
        cache.remove(key)
                .as(StepVerifier::create)
                .expectNextCount(0) // No value expected as Mono<Void> is empty
                .verifyComplete();

        // Verify that the value was removed
        Mono<Boolean> result = cache.get(key);
        StepVerifier.create(result)
                .expectComplete() // No value should be present
                .verify();
    }

    @Test
    void testRemoveNonExistentKey() {
        // Define the key that does not exist in the cache
        String key = "nonExistentKey";

        // Call the remove method
        cache.remove(key)
                .as(StepVerifier::create)
                .expectNextCount(0) // No value expected as Mono<Void> is empty
                .verifyComplete();

        // Verify that no exception or error occurs
    }
}
