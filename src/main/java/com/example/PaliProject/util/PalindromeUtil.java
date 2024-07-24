package com.example.PaliProject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class PalindromeUtil {

    private static final Logger logger = LoggerFactory.getLogger(PalindromeUtil.class);

    /**
     * Checks if the input text is valid.
     *
     * @param text The input text.
     * @return true if the text is not null and contains only alphabetic characters; false otherwise.
     */
    public boolean isValidInput(String text) {
        if (text == null) {
            logger.warn("Input text is null.");
            return false;
        }
        boolean isValid = text.matches("[a-zA-Z]+");
        if (!isValid) {
            logger.warn("Input text contains invalid characters: {}", text);
        }
        return isValid;
    }

    /**
     * Checks if the input text is a palindrome.
     *
     * @param text The input text.
     * @return true if the text is a palindrome; false otherwise.
     */
    public boolean isPalindrome(String text) {

        if (text == null) {
            logger.error("Input text is null.");
            return false;
        }

        text = text.trim().toLowerCase();  // Normalize text: trim spaces and convert to lowercase

        int length = text.length();
        // A single character and empty strings are palindromes
        if (length < 2) {
            return true;
        }

        // Check if each character is mirrored
        for (int i = 0; i < length / 2; i++) {
            if (text.charAt(i) != text.charAt(length - i - 1)) {
                logger.debug("Mismatch found: {} != {}", text.charAt(i), text.charAt(length - i - 1));
                return false;
            }
        }
        return true;
    }
}
