package com.example.pokedex.dto;

import com.example.pokedex.entity.Role;

public record UserResponse(
        Long id,
        String name,
        String email,
        String password,
        Role role
) {
}
