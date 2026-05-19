package com.example.pokedex.service;

import com.example.pokedex.dto.UserDto;
import com.example.pokedex.dto.UserResponse;
import com.example.pokedex.entity.User;
import com.example.pokedex.repository.UserRepository;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@AllArgsConstructor
public class UserService {
    private final UserRepository repository;
    private final PasswordEncoder encoder;

    public UserResponse createUser(UserDto dto){
        boolean exists = repository.existsByEmail(dto.email());
        if (exists){
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "Email ja existente"
            );
        }
        User u = new User();

        u.setName(dto.name());
        u.setEmail(dto.email());
        u.setRole(dto.role());

        String encode = encoder.encode(dto.password());
        u.setPassword(encode);

        repository.save(u);

        return toResponse(u);

    }

    public List<UserResponse> getAllUsers(){
        List<User> users = repository.findAll();
        return users.stream().map(this::toResponse)
                .toList();
    }


    public UserResponse updateUser(Long id, UserDto dto){
        User user = repository.findById(id).orElseThrow(() -> new ResponseStatusException(
                HttpStatus.NOT_FOUND,
                "Usuario não encontraa"
        ));
        boolean exists = repository.existsByEmailAndIdNot(dto.email(), id);

        if (exists) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email já está em uso"
            );
        }

        user.setName(dto.name());
        user.setEmail(dto.email());

        repository.save(user);

        return toResponse(user);

    }

    public void deleteUserById(Long id){
        if (!repository.existsById(id)){
            throw new ResponseStatusException(
                    HttpStatus.FORBIDDEN,
                    "Email já está em uso"
            );
        }
        repository.deleteById(id);
    }




    public UserResponse toResponse(User user){
        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPassword(),
                user.getRole()
        );
    }

}
