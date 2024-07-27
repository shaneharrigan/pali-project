Feature: Palindrome checker

  Scenario: Check if a string is a palindrome
    Given the input string is "abba" and username is "Joe Bloggs"
    When I check if the string is a palindrome
    Then the result should be "true"

  Scenario: Check if a string is not a palindrome
    Given the input string is "hello" and username is "Joe Bloggs"
    When I check if the string is a palindrome
    Then the result should be "false"
