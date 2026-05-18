package com.example.pokedex.entity;


import com.example.pokedex.dto.PokemonDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.Set;

@Entity
@Table(name = "pokemons")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Pokemons {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false, unique = true)
    String name;

    @Column(nullable = false)
    String urlImgPokemon;

    @Column(nullable = false, unique = true)
    Long pokemonNumber;

    @ElementCollection
    @Enumerated(EnumType.STRING)
    @Size(min = 1, max = 2)
    private Set<Type> types;

}
