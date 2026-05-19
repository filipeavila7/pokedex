package com.example.pokedex.dto;

import com.example.pokedex.entity.Role;

public record UserDto(
        String name,
        String email,
        String password,
        Role role
) {
}
