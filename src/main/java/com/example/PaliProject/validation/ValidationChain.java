package com.example.PaliProject.validation;

import java.util.ArrayList;
import java.util.List;

/**
 * Chain of validation rules for validating text input.
 * <p>
 * This class allows for chaining multiple validation rules and validating text against them
 * based on a specified mode: either all rules must pass or at least one rule must pass.
 * </p>
 */
public class ValidationChain {
    private final List<IValidationRule> rules = new ArrayList<>();
    private final ValidationMode mode;

    /**
     * Enum representing the validation modes.
     * <p>
     * ALL: All validation rules must pass for the validation to succeed.
     * ANY: At least one validation rule must pass for the validation to succeed.
     * </p>
     */
    public enum ValidationMode {
        ALL, ANY
    }

    /**
     * Constructs a {@code ValidationChain} with the specified validation mode.
     *
     * @param mode the validation mode to be used (ALL or ANY)
     */
    public ValidationChain(ValidationMode mode) {
        this.mode = mode;
    }

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
     * Validates the given text against the rules in the chain based on the validation mode.
     *
     * @param text the input text to be validated
     * @return {@code true} if the text is valid according to the validation mode and rules;
     *         {@code false} otherwise
     */
    public boolean validate(String text) {
        if (mode == ValidationMode.ALL) {
            for (IValidationRule rule : rules) {
                if (!rule.isValid(text)) {
                    return false;
                }
            }
            return true;
        } else if (mode == ValidationMode.ANY) {
            for (IValidationRule rule : rules) {
                if (rule.isValid(text)) {
                    return true;
                }
            }
            return false;
        }
        return false;
    }
}
