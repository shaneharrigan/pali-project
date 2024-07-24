package com.example.PaliProject.persistence;

import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;

public interface PersistenceService {
    Mono<Void> save(String username, String text, boolean isPalindrome);
    Mono<Map<String, Boolean>> loadCache() throws IOException;
}
