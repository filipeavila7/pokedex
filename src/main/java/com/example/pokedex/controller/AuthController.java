package com.example.pokedex.controller;

import com.example.pokedex.dto.LoginDto;
import com.example.pokedex.dto.LoginResponse;
import com.example.pokedex.service.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService service;

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginDto request) {
        return ResponseEntity.ok(service.login(request));
    }
}
