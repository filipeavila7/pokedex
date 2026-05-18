package com.example.pokedex.dto;

import com.example.pokedex.entity.Type;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.util.Set;

public record PokemonDto(

        @NotBlank
        String name,

        @NotNull
        Long number,

        @NotBlank
        String urlImg,

        @Size(min = 1, max = 2)
        Set<Type> types

) {
}
