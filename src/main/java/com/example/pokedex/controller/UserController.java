package com.example.pokedex.controller;

import com.example.pokedex.dto.UserDto;
import com.example.pokedex.dto.UserResponse;
import com.example.pokedex.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
@AllArgsConstructor
public class UserController {
    private final UserService service;

    @PostMapping
    ResponseEntity<UserResponse> createUser(@RequestBody UserDto data){
        return ResponseEntity.ok(service.createUser(data));
    }

    @GetMapping
    ResponseEntity<List<UserResponse>> getAllUsers(){
        return ResponseEntity.ok(service.getAllUsers());
    }

    @DeleteMapping("/{id}")
    ResponseEntity<Void> deleteUser(@PathVariable Long id){
        service.deleteUserById(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updatePokemon(@PathVariable Long id, @RequestBody UserDto data){
        return ResponseEntity.ok(service.updateUser(id, data ));
    }
}
