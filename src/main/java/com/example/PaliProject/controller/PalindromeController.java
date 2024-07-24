package com.example.PaliProject.controller;

import com.example.PaliProject.service.PalindromeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.io.IOException;

@RestController
@RequestMapping("/api/palindrome")
public class PalindromeController {
    private final PalindromeService palindromeService;

    @Autowired
    public PalindromeController(PalindromeService palindromeService) {
        this.palindromeService = palindromeService;
    }


    @PostMapping
    public Mono<Boolean> checkPalindrome(@RequestParam String username, @RequestParam String text) {
        return palindromeService.isPalindrome(username,text);
    }
}
