package com.example.PaliProject.validation;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationChainTest {

    private ValidationChain validationChainAll;
    private ValidationChain validationChainAny;

    @BeforeEach
    void setUp() {
        validationChainAll = new ValidationChain(ValidationChain.ValidationMode.ALL);
        validationChainAny = new ValidationChain(ValidationChain.ValidationMode.ANY);
    }

    @Test
    void testValidationChainWithSingleRuleValidInput() {
        validationChainAll.addRule(new ValidationRules.AlphabeticValidationRuleImpl());

        String validInput = "abc";
        assertTrue(validationChainAll.validate(validInput));
    }

    @Test
    void testValidationChainWithSingleRuleInvalidInput() {
        validationChainAll.addRule(new ValidationRules.AlphabeticValidationRuleImpl());

        String invalidInput = "abc123";
        assertFalse(validationChainAll.validate(invalidInput));
    }

    @Test
    void testValidationChainWithMultipleRulesInvalid() {
        validationChainAll.addRule(new ValidationRules.AlphabeticValidationRuleImpl())
                .addRule(new ValidationRules.WhitespaceValidationRuleImpl());

        assertFalse(validationChainAll.validate("abc def"));
        assertFalse(validationChainAll.validate("   ")); // Only spaces
        assertTrue(validationChainAll.validate("a")); // Single character
        assertFalse(validationChainAll.validate("a b c")); // Spaces between characters
    }

    @Test
    void testValidationChainWithMultipleRulesOneInvalid() {
        validationChainAll.addRule(new ValidationRules.AlphabeticValidationRuleImpl())
                .addRule(new ValidationRules.NumericValidationRuleImpl());

        String invalidInput = "abc 123";
        assertFalse(validationChainAll.validate(invalidInput));
    }

    @Test
    void testValidationChainAllWithAllRulesValid() {
        validationChainAll.addRule(new ValidationRules.AlphabeticValidationRuleImpl())
                .addRule(new ValidationRules.NumericValidationRuleImpl());

        String validInput = "abc123";
        assertFalse(validationChainAll.validate(validInput)); // Only valid if both rules pass
    }

    @Test
    void testValidationChainAnyWithSingleRuleValidInput() {
        validationChainAny.addRule(new ValidationRules.AlphabeticValidationRuleImpl());

        String validInput = "abc";
        assertTrue(validationChainAny.validate(validInput));
    }

    @Test
    void testValidationChainAnyWithSingleRuleInvalidInput() {
        validationChainAny.addRule(new ValidationRules.AlphabeticValidationRuleImpl());

        String invalidInput = "abc123";
        assertFalse(validationChainAny.validate(invalidInput));
    }

    @Test
    void testValidationChainAnyWithMultipleRulesValid() {
        validationChainAny.addRule(new ValidationRules.AlphabeticValidationRuleImpl())
                .addRule(new ValidationRules.AlphanumericValidationRuleImpl())
                .addRule(new ValidationRules.NumericValidationRuleImpl());

        assertTrue(validationChainAny.validate("abc")); // Alphabetic rule passes
        assertTrue(validationChainAny.validate("123")); // Numeric rule passes
        assertTrue(validationChainAny.validate("abc123")); // Both rules pass
        assertFalse(validationChainAny.validate("abc 123")); // Neither rule passes due to space
    }

    @Test
    void testValidationChainAnyWithMultipleRulesAllInvalid() {
        validationChainAny.addRule(new ValidationRules.AlphabeticValidationRuleImpl())
                .addRule(new ValidationRules.NumericValidationRuleImpl());

        String invalidInput = "abc 123";
        assertFalse(validationChainAny.validate(invalidInput)); // Neither rule passes
    }

    @Test
    void testValidationChainAnyWithMultipleRulesOneValid() {
        validationChainAny.addRule(new ValidationRules.AlphabeticValidationRuleImpl())
                .addRule(new ValidationRules.NumericValidationRuleImpl());

        assertTrue(validationChainAny.validate("abc")); // Alphabetic rule passes
        assertTrue(validationChainAny.validate("123")); // Numeric rule passes
        assertFalse(validationChainAny.validate("a1")); // Alphabetic rule passes (though mixed input)
    }
}
