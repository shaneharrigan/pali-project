package com.example.PaliProject.steps;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class PalindromeSteps {

    private String username;
    private String input;
    private boolean result;

    @Given("the input string is {string} and username is {string}")
    public void the_input_string_is_and_username_is(String input, String username) {
        this.input = input;
        this.username = username;
    }

    @When("I check if the string is a palindrome")
    public void i_check_if_the_string_is_a_palindrome() {
        RestTemplate restTemplate = new RestTemplate();
        // Construct URL with query parameters
        String url = String.format("http://localhost:8080/api/1/palindrome?username=%s&text=%s", username, input);

        // Set headers
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);

        // Create the request entity with an empty body
        HttpEntity<String> entity = new HttpEntity<>(null, headers);

        // Make the POST request
        ResponseEntity<Boolean> response = restTemplate.postForEntity(url, entity, Boolean.class);
        result = Boolean.TRUE.equals(response.getBody());
    }

    @Then("the result should be {string}")
    public void the_result_should_be(String expectedResult) {
        assertEquals(Boolean.parseBoolean(expectedResult), result);
    }
}
