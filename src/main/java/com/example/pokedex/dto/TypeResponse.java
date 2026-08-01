package com.example.pokedex.dto;

import com.example.pokedex.entity.Type;

import java.util.Set;

public record TypeResponse(
        Long id,
        String name,
        String number,
        String urlImg,
        Set<Type> types,
        Set<Type> weakness
) {
}
