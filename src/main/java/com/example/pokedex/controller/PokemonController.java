package com.example.pokedex.controller;

import com.example.pokedex.dto.PokemonDto;
import com.example.pokedex.dto.PokemonResponse;
import com.example.pokedex.dto.TypeResponse;
import com.example.pokedex.service.PokemonService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pokemons")
@AllArgsConstructor
public class PokemonController {
    private final PokemonService service;

    // === POST ===
    @PostMapping
    public ResponseEntity<PokemonResponse> createPokemon(@Valid @RequestBody PokemonDto data){
        return ResponseEntity.ok(service.createPokemon(data));
    }

    // === GET ===
    @GetMapping("/{name}")
    public ResponseEntity<TypeResponse>  getPokemonByName(@PathVariable String name){
        return ResponseEntity.ok(service.getPokemonByName(name));
    }

    @GetMapping()
    public ResponseEntity<Page<PokemonResponse>> getAllPokemons(@RequestParam(defaultValue = "0") int page){
        return ResponseEntity.ok(service.getAllPokemons(page));
    }

    // rota de pesquisa por nome ou numero
    @GetMapping("/search")
    public ResponseEntity<List<PokemonResponse>> search (@RequestParam String pokemon){
        return ResponseEntity.ok(service.search(pokemon));

    }

    // rota de sugestão apenas de nome
    @GetMapping("/suggestions")
    public ResponseEntity<List<PokemonResponse>> suggestions (@RequestParam String pokemon){
        return ResponseEntity.ok(service.suggestions(pokemon));

    }



    // === PUT ===
    @PutMapping("/{id}")
    public ResponseEntity<PokemonResponse> updatePokemon(@PathVariable Long id, @RequestBody PokemonDto data){
        return ResponseEntity.ok(service.updatePokemon(id, data ));
    }

    // === DELETE ===
    @DeleteMapping("{id}")
    public ResponseEntity<Void> deletePokemon(@PathVariable Long id){
        service.deletePokemon(id);
        return ResponseEntity.noContent().build();
    }
}
