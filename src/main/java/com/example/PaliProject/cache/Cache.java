package com.example.PaliProject.cache;

import reactor.core.publisher.Mono;

public interface Cache {
    Mono<Boolean> get(String key);
    Mono<Void> put(String key, Boolean value);
    Mono<Void> remove(String key);
}
