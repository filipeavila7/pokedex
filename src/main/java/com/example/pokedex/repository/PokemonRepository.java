package com.example.pokedex.repository;

import com.example.pokedex.entity.Pokemons;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;


public interface PokemonRepository extends JpaRepository<Pokemons, Long> {
    boolean existsByNameAndPokemonNumber(String name, Long pokemonNumber);
    Pokemons findByName(String name);


    boolean existsByNameAndPokemonNumberAndIdNot(
            String name,
            Long pokemonNumber,
            Long id
    );


    // Busca pokemons pelo nome
    List<Pokemons> findByNameContainingIgnoreCase(String name);

    // Sugestões começando pela letra
    List<Pokemons> findByNameStartingWithIgnoreCase(String name);

    // Busca exata por número
    Optional<Pokemons> findByPokemonNumber(Long pokemonNumber);
}
