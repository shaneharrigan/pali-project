package com.example.PaliProject.service;

import com.example.PaliProject.persistence.PersistenceService;
import com.example.PaliProject.util.PalindromeUtil;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.io.IOException;
import java.util.Map;
import java.util.HashMap;

@Service
public class PalindromeService {
    private final PalindromeUtil palindromeUtil;
    private final PersistenceService persistenceService;
    private final Map<String, Boolean> cache = new HashMap<>();

    // Constructor injection
    public PalindromeService(PalindromeUtil palindromeUtil, PersistenceService persistenceService) {
        this.palindromeUtil = palindromeUtil;
        this.persistenceService = persistenceService;
    }

    public void initialize() throws IOException {
        this.persistenceService.loadCache().subscribe(cache::putAll);
    }

    public Mono<Boolean> isPalindrome(String username, String text) {
        if (!palindromeUtil.isValidInput(text)) {
            return Mono.error(new IllegalArgumentException("Invalid text input: only alphabetic characters are supported"));
        }

        if (cache.containsKey(text)) {
            return Mono.just(cache.get(text));
        }

        boolean isPalindrome = palindromeUtil.isPalindrome(text);
        cache.put(text, isPalindrome);
        return persistenceService.save(username, text, isPalindrome)
                .then(Mono.just(isPalindrome));
    }
}
