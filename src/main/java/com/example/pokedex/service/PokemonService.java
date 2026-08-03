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

import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class PokemonService {
    private final PokemonRepository repository;
    private final TypeEffectivenessService weakService;

    // contar total de pokemons
    public long countPokemons() {
        return repository.count();
    }

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
    public Page<PokemonResponse> getAllPokemons(Pageable pageable){
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

        Pokemons previous = repository
                .findFirstByPokemonNumberLessThanOrderByPokemonNumberDesc(find.getPokemonNumber())
                .orElse(null);


        Pokemons next = repository
                .findFirstByPokemonNumberGreaterThanOrderByPokemonNumberAsc(find.getPokemonNumber())
                .orElse(null);

        return toTypeResponse(find, weakness, previous, next);

    }

    // pesquisa de pokemons

    public Page<PokemonResponse> search(String p, Pageable pageable){

        // caso for numero
        if (p.matches("\\d+")){
            String number = String.format("%03d", Integer.parseInt(p));

            return repository.findByPokemonNumber(number, pageable)
                    .map(this::toResponse);

        }

        return repository.findByNameContainingIgnoreCase(p, pageable)
                .map(this::toResponse);

    }

    // sugestões
    public List<PokemonResponse> suggestions (String p){
        return repository.findByNameStartingWithIgnoreCase(p)
                .stream()
                .limit(5)
                .map(this::toResponse)
                .toList();
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

    TypeResponse toTypeResponse(Pokemons p, Set<Type> weakeness, Pokemons previous, Pokemons next){
        return new TypeResponse(
                p.getId(),
                p.getName(),
                p.getPokemonNumber(),
                FileUrlUtils.toPublicUrl(p.getUrlImgPokemon()),
                p.getTypes(),
                weakeness,
                next != null ? toResponse(next) : null,
                previous != null ? toResponse(previous) : null
        );
    }




}
