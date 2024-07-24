package com.example.PaliProject.persistence;

import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

@Component
public final class FilePersistenceService implements PersistenceService {
    private static final String FILE_PATH = "palindromes.txt";

    @Override
    public Mono<Void> save(String username, String text, boolean isPalindrome) {
        return Mono.fromRunnable(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(String.format("%s,%s,%b%n", username, text, isPalindrome));
            } catch (IOException e) {
                throw new RuntimeException("Error writing to file", e);
            }
        });
    }

    @Override
    public Mono<Map<String, Boolean>> loadCache() {
        return Mono.fromCallable(() -> {
            Map<String, Boolean> cache = new HashMap<>();
            File file = new File(FILE_PATH);
            if (file.exists()) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    String line;
                    while ((line = reader.readLine()) != null) {
                        String[] parts = line.split(",");
                        if (parts.length == 3) {
                            String text = parts[1];
                            boolean isPalindrome = Boolean.parseBoolean(parts[2]);
                            System.out.println(String.format("found %s", text));
                            cache.put(text, isPalindrome);
                        }
                    }
                }
            }
            return cache;
        });
    }
}
