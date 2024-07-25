package com.example.PaliProject.cache;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;


class InMemoryCacheTest {

    private InMemoryCache cache;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        cache = new InMemoryCache();
    }

    @Test
    void testPut() {
        String key = "testKey";
        Boolean value = true;

        cache.put(key, value)
                .as(StepVerifier::create)
                .expectNextCount(0) // No value expected as Mono<Void> is empty
                .verifyComplete();

        Mono<Boolean> result = cache.get(key);
        StepVerifier.create(result)
                .expectNext(value)
                .verifyComplete();
    }

    @Test
    void testGetCacheHit() {
        String key = "testKey";
        Boolean value = true;
        cache.put(key, value).block();

        Mono<Boolean> result = cache.get(key);
        StepVerifier.create(result)
                .expectNext(value)
                .verifyComplete();
    }

    @Test
    void testGetCacheMiss() {
        String key = "nonExistentKey";

        Mono<Boolean> result = cache.get(key);
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void testRemove() {
        String key = "testKey";
        Boolean value = true;
        cache.put(key, value).block();

        cache.remove(key)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

        Mono<Boolean> result = cache.get(key);
        StepVerifier.create(result)
                .expectComplete()
                .verify();
    }

    @Test
    void testRemoveNonExistentKey() {
        String key = "nonExistentKey";

        cache.remove(key)
                .as(StepVerifier::create)
                .expectNextCount(0)
                .verifyComplete();

    }
}
