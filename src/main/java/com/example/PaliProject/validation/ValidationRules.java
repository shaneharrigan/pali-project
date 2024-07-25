package com.example.PaliProject.validation;

/**
 * Contains various validation rule implementations for text input.
 * <p>
 * These implementations can be used to define specific criteria for validating text input.
 * </p>
 */
public class ValidationRules {

    /**
     * Validation rule to check if the input text contains only alphabetic characters.
     */
    public static class AlphabeticValidationRuleImpl implements IValidationRule {
        @Override
        public boolean isValid(String text) {
            return text != null && text.matches("[a-zA-Z]+");
        }
    }

    /**
     * Validation rule to check if the input text contains only numeric characters.
     */
    public static class NumericValidationRuleImpl implements IValidationRule {
        @Override
        public boolean isValid(String text) {
            return text != null && text.matches("[0-9]+");
        }
    }

    /**
     * Validation rule to check if the input text contains only alphanumeric characters.
     */
    public static class AlphanumericValidationRuleImpl implements IValidationRule {
        @Override
        public boolean isValid(String text) {
            return text != null && text.matches("[a-zA-Z0-9]+");
        }
    }

    /**
     * Validation rule to check if the input text contains only alphabetic characters and spaces.
     */
    public static class WhitespaceValidationRuleImpl implements IValidationRule {
        @Override
        public boolean isValid(String text) {
            return text != null && text.matches("[a-zA-Z0-9\\s]*");
        }
    }
}
