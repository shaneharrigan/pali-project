package com.example.PaliProject.validation;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ValidationRulesTest {

    @Test
    void testAlphabeticValidationRule() {
        IValidationRule rule = new ValidationRules.AlphabeticValidationRuleImpl();

        assertTrue(rule.isValid("abc"));
        assertFalse(rule.isValid("abc123"));
        assertFalse(rule.isValid("123"));
        assertFalse(rule.isValid("abc def"));
        assertFalse(rule.isValid(null));
    }

    @Test
    void testNumericValidationRule() {
        IValidationRule rule = new ValidationRules.NumericValidationRuleImpl();

        assertTrue(rule.isValid("123"));
        assertFalse(rule.isValid("abc123"));
        assertFalse(rule.isValid("abc"));
        assertFalse(rule.isValid("123 456"));
        assertFalse(rule.isValid(null));
    }

    @Test
    void testAlphanumericValidationRule() {
        IValidationRule rule = new ValidationRules.AlphanumericValidationRuleImpl();

        assertTrue(rule.isValid("abc123"));
        assertTrue(rule.isValid("123"));
        assertTrue(rule.isValid("abc"));
        assertFalse(rule.isValid("abc 123"));
        assertFalse(rule.isValid(null));
    }

    @Test
    void testWhitespaceValidationRule() {
        IValidationRule rule = new ValidationRules.WhitespaceValidationRuleImpl();

        assertTrue(rule.isValid("abc def"));
        assertTrue(rule.isValid("abc123"));
        assertTrue(rule.isValid("abc"));
        assertTrue(rule.isValid("123"));
        assertFalse(rule.isValid(null));
    }
}
