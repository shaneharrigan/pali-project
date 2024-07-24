package com.example.PaliProject.util;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PalindromeUtilTest {

    private PalindromeUtil palindromeUtil;

    @BeforeEach
    void setUp() {
        palindromeUtil = new PalindromeUtil();
    }

    @Test
    void isValidInput() {
        // Valid inputs
        assertTrue(palindromeUtil.isValidInput("madam"));
        assertTrue(palindromeUtil.isValidInput("Racecar"));

        // Invalid inputs
        assertFalse(palindromeUtil.isValidInput("madam123"));
        assertFalse(palindromeUtil.isValidInput("hello world"));
        assertFalse(palindromeUtil.isValidInput(""));

        // Null input
        assertFalse(palindromeUtil.isValidInput(null));
    }

    @Test
    void isPalindrome() {
        // Palindromes
        assertTrue(palindromeUtil.isPalindrome("madam"));
        assertTrue(palindromeUtil.isPalindrome("Racecar"));
        assertTrue(palindromeUtil.isPalindrome("A"));
        assertTrue(palindromeUtil.isPalindrome(""));

        // Non-palindromes
        assertFalse(palindromeUtil.isPalindrome("hello"));
        assertFalse(palindromeUtil.isPalindrome("world"));

        // Case-insensitive checks
        assertFalse(palindromeUtil.isPalindrome("Ablewasiwaselba"));
        assertFalse(palindromeUtil.isPalindrome("Palindrome"));

        // Invalid input
        assertFalse(palindromeUtil.isPalindrome(null));
    }
}
