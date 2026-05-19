package com.example.pokedex.service;

import com.example.pokedex.dto.LoginDto;
import com.example.pokedex.dto.LoginResponse;
import com.example.pokedex.entity.User;
import com.example.pokedex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserRepository repository;

    // faz login
    public LoginResponse login(LoginDto dto){
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        dto.email(),
                        dto.password()
                )
        );

        User user = repository.findByEmail(dto.email())
                .orElseThrow();

        // cria o token para o usuario
        String token = jwtService.generateToken(user);

        // retrona o token na resposta
        return new LoginResponse(token);

    }
}
