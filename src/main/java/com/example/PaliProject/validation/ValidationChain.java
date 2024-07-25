package com.example.PaliProject.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Chain of validation rules for validating text input.
 * <p>
 * This class allows for chaining multiple validation rules and validating text against all of them.
 * </p>
 */
public class ValidationChain {
    private final List<IValidationRule> rules = new ArrayList<>();

    /**
     * Adds a validation rule to the chain.
     *
     * @param rule the validation rule to be added
     * @return the current instance of {@code ValidationChain} for method chaining
     */
    public ValidationChain addRule(IValidationRule rule) {
        rules.add(rule);
        return this;
    }

    /**
     * Validates the given text against all the rules in the chain.
     *
     * @param text the input text to be validated
     * @return {@code true} if the text is valid according to all the rules; {@code false} otherwise
     */
    public boolean validate(String text) {
        for (IValidationRule rule : rules) {
            if (!rule.isValid(text)) {
                return false;
            }
        }
        return true;
    }
}
