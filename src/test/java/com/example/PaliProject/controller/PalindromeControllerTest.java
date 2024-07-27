package com.example.PaliProject.controller;

import com.example.PaliProject.service.PalindromeService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = PalindromeController.class)
class PalindromeControllerTest {

    @Autowired
    private WebTestClient webTestClient;

    @MockBean
    private PalindromeService palindromeService;

    @BeforeEach
    void setUp() {
        when(palindromeService.isPalindrome(anyString(), anyString()))
                .thenReturn(Mono.just(true));
    }

    @Test
    void testCheckPalindromeValid() {
        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/1/palindrome")
                        .queryParam("username", "ted")
                        .queryParam("text", "aba")
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Boolean.class)
                .isEqualTo(true);

        Mockito.verify(palindromeService).isPalindrome("ted", "aba");
    }

    @Test
    void testCheckPalindromeInvalid() {
        when(palindromeService.isPalindrome(anyString(), anyString()))
                .thenReturn(Mono.error(new IllegalArgumentException("Invalid text input: only alphabetic characters are supported")));

        webTestClient.post()
                .uri(uriBuilder -> uriBuilder.path("/api/1/palindrome")
                        .queryParam("username", "ted")
                        .queryParam("text", "123")
                        .build())
                .exchange()
                .expectStatus().isBadRequest()
                .expectBody(String.class)
                .isEqualTo("Invalid text input: only alphabetic characters are supported");

        Mockito.verify(palindromeService).isPalindrome("ted", "123");
    }
}