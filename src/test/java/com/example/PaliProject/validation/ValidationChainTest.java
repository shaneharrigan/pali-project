package com.example.PaliProject.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationChainTest {

    private ValidationChain validationChain;

    @BeforeEach
    void setUp() {
        validationChain = new ValidationChain();
    }

    @Test
    void testValidationChainWithSingleRuleValidInput() {
        validationChain.addRule(new ValidationRules.AlphabeticValidationRuleImpl());

        String validInput = "abc";
        assertTrue(validationChain.validate(validInput));
    }

    @Test
    void testValidationChainWithSingleRuleInvalidInput() {
        validationChain.addRule(new ValidationRules.AlphabeticValidationRuleImpl());

        String invalidInput = "abc123";
        assertFalse(validationChain.validate(invalidInput));
    }

    @Test
    void testValidationChainWithMultipleRulesInvalid() {
        validationChain.addRule(new ValidationRules.AlphabeticValidationRuleImpl())
                .addRule(new ValidationRules.WhitespaceValidationRuleImpl());

        assertFalse(validationChain.validate("abc def"));
        assertFalse(validationChain.validate("   ")); // Only spaces
        assertTrue(validationChain.validate("a")); // Single character
        assertFalse(validationChain.validate("a b c")); // Spaces between characters
    }

    @Test
    void testValidationChainWithMultipleRulesOneInvalid() {
        validationChain.addRule(new ValidationRules.AlphabeticValidationRuleImpl())
                .addRule(new ValidationRules.NumericValidationRuleImpl());

        String invalidInput = "abc 123";
        assertFalse(validationChain.validate(invalidInput));
    }
}
