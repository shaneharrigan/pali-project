package com.example.PaliProject.validation;

/**
 * Interface for defining validation rules for text input.
 * <p>
 * Implementations of this interface can define specific validation logic for text input.
 * </p>
 */
public interface IValidationRule {
    /**
     * Validates the given text based on specific criteria.
     *
     * @param text the input text to be validated
     * @return {@code true} if the text is valid according to the rule; {@code false} otherwise
     */
    boolean isValid(String text);
}
