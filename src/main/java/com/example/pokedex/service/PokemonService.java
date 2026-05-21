package com.example.pokedex.service;

import com.example.pokedex.dto.PokemonDto;
import com.example.pokedex.dto.PokemonResponse;
import com.example.pokedex.dto.TypeResponse;
import com.example.pokedex.entity.Pokemons;
import com.example.pokedex.entity.Type;
import com.example.pokedex.repository.PokemonRepository;
import com.example.pokedex.utils.FileUrlUtils;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository repository;
    private final TypeEffectivenessService weakService;

    public PokemonResponse createPokemon(PokemonDto dto){
        boolean exists = repository.existsByNameAndPokemonNumber(dto.name(), dto.number());
        // caso ja exista
        if (exists){
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Pokémon já existe"
            );
        }
        // cria o pokemon
        Pokemons p = new Pokemons();

        p.setName(dto.name());
        p.setPokemonNumber(dto.number());
        p.setTypes(dto.types());
        p.setUrlImgPokemon(dto.urlImg());

        return toResponse(repository.save(p));

    }

    // get de todos os pokemons retorna uma pagina com 12 pokemons
    public Page<PokemonResponse> getAllPokemons(int page){
        Pageable pageable = PageRequest.of(page, 12);

        return repository.findAll(pageable).map(this::toResponse);
    }


    public void deletePokemon(Long id){
        if (!repository.existsById(id)){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pokémon não encontrado"
            );
        }

        repository.deleteById(id);
    }

    public PokemonResponse updatePokemon(Long id, PokemonDto dto) {

        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Pokémon não encontrado");
        }

        boolean exists = repository.existsByNameAndPokemonNumberAndIdNot(
                dto.name(),
                dto.number(),
                id
        );

        if (exists) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Pokémon já existe");
        }

        Pokemons p = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Pokémon não encontrado"
                ));

        p.setName(dto.name());
        p.setPokemonNumber(dto.number());
        p.setTypes(dto.types());
        p.setUrlImgPokemon(dto.urlImg());

        return toResponse(repository.save(p));
    }

    public TypeResponse getPokemonByName(String name){
        Pokemons find = repository.findByName(name);
        if (find == null){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Pokémon não encontrado"
            );
        }
        Set<Type> pTypes = find.getTypes();

        Set<Type> weakness = weakService.calculateWeaknesses(pTypes);

        return toTypeResponse(find, weakness);

    }

    // resposta personalizada
    PokemonResponse toResponse(Pokemons p){
        return new PokemonResponse(
                p.getId(),
                p.getName(),
                p.getPokemonNumber(),
                FileUrlUtils.toPublicUrl(p.getUrlImgPokemon()),
                p.getTypes()
        );
    }

    TypeResponse toTypeResponse(Pokemons p, Set<Type> weakeness){
        return new TypeResponse(
                p.getId(),
                p.getName(),
                p.getPokemonNumber(),
                FileUrlUtils.toPublicUrl(p.getUrlImgPokemon()),
                p.getTypes(),
                weakeness
        );
    }




}
