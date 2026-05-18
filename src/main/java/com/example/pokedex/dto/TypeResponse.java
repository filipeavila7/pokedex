package com.example.pokedex.dto;

import com.example.pokedex.entity.Type;

import java.util.Set;

public record TypeResponse(
        String name,
        Long number,
        String urlImg,
        Set<Type> types,
        Set<Type> weakness
) {
}
