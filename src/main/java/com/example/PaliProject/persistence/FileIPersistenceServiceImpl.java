package com.example.PaliProject.persistence;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * An implementation of {@link IPersistenceService} that persists data to a file.
 * This service writes and reads palindrome data to and from a text file.
 * The file used for persistence is specified by {@link #FILE_PATH}.
 * <p>
 * This class is marked as a Spring {@link Component}, allowing it to be managed by the Spring container.
 * </p>
 */
@Component
public final class FileIPersistenceServiceImpl implements IPersistenceService {

    private static final Logger logger = LoggerFactory.getLogger(FileIPersistenceServiceImpl.class);
    private static final String FILE_PATH = "palindromes.txt";

    /**
     * Saves the provided palindrome data to the file.
     * <p>
     * This method appends the given data to the file specified by {@link #FILE_PATH}.
     * The data is written in the format: "username,text,isPalindrome", with each entry on a new line.
     * </p>
     *
     * @param username     the username associated with the data to be saved
     * @param text         the text to be saved
     * @param isPalindrome {@code true} if the text is a palindrome, {@code false} otherwise
     * @return a {@link Mono} that completes when the data has been successfully written to the file
     */
    @Override
    public Mono<Void> save(String username, String text, boolean isPalindrome) {
        System.out.println("FIRRRRRRREEEE");
        return Mono.fromRunnable(() -> {
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
                writer.write(String.format("%s,%s,%b%n", username, text, isPalindrome));
                logger.info("Saved data to file: username={}, text={}, isPalindrome={}", username, text, isPalindrome);
            } catch (IOException e) {
                logger.error("Error writing to file: {}", FILE_PATH, e);
                throw new RuntimeException("Error writing to file", e);
            }
        });
    }

    /**
     * Loads the cached palindrome data from the file.
     * <p>
     * This method reads the data from the file specified by {@link #FILE_PATH}.
     * Each line in the file is parsed into a map entry, where the key is the text and the value is a boolean
     * indicating whether the text is a palindrome.
     * </p>
     *
     * @return a {@link Mono} containing a map of the cached data, where the key is the text and the value is a
     * boolean indicating if the text is a palindrome
     */
    @Override
    public Mono<Map<String, Boolean>> loadCache() {
        return Mono.fromCallable(() -> {
            Map<String, Boolean> cache = new HashMap<>();
            File file = new File(FILE_PATH);

            // Check if the file exists and create it if it doesn't
            if (!file.exists()) {
                try {
                    boolean created = file.createNewFile();
                    if (created) {
                        logger.info("Cache file created: {}", FILE_PATH);
                    } else {
                        logger.warn("Cache file already exists: {}", FILE_PATH);
                    }
                } catch (IOException e) {
                    logger.error("Error creating cache file: {}", FILE_PATH, e);
                    throw new RuntimeException("Error creating cache file", e);
                }
            }

            // Read from the file
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    String[] parts = line.split(",");
                    if (parts.length == 3) {
                        String text = parts[1];
                        boolean isPalindrome = Boolean.parseBoolean(parts[2]);
                        cache.put(text, isPalindrome);
                    }
                }
                logger.info("Loaded cache from file: {}", FILE_PATH);
            } catch (IOException e) {
                logger.error("Error reading from file: {}", FILE_PATH, e);
                throw new RuntimeException("Error reading from file", e);
            }

            return cache;
        });
    }

}
