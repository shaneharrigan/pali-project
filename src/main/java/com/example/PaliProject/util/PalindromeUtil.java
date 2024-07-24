package com.example.PaliProject.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * Utility class for palindrome-related operations.
 * <p>
 * This class provides methods to validate input text and to determine if a given text is a palindrome.
 * It also includes logging to assist with debugging and validation.
 * </p>
 */
@Component
public class PalindromeUtil {

    private static final Logger logger = LoggerFactory.getLogger(PalindromeUtil.class);

    /**
     * Checks if the input text is valid.
     * <p>
     * A valid input is non-null and contains only alphabetic characters (a-z, A-Z).
     * </p>
     *
     * @param text the input text to be validated
     * @return {@code true} if the text is valid (non-null and contains only alphabetic characters);
     *         {@code false} otherwise
     */
    public boolean isValidInput(String text) {
        if (text == null) {
            logger.warn("Input text is null.");
            return false;
        }
        //Regex magic...
        boolean isValid = text.matches("[a-zA-Z]+");
        if (!isValid) {
            logger.warn("Input text contains invalid characters: {}", text);
        }
        return isValid;
    }

    /**
     * Checks if the input text is a palindrome.
     * <p>
     * This method determines if the text reads the same backward as forward. The text is normalized by
     * trimming whitespace and converting to lowercase before checking.
     * </p>
     *
     * @param text the input text to be checked
     * @return {@code true} if the text is a palindrome; {@code false} otherwise
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
