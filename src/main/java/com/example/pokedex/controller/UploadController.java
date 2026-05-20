package com.example.pokedex.controller;

import com.example.pokedex.service.UploadService;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/upload")
@AllArgsConstructor
public class UploadController {

    private final UploadService uploadService;

    @PostMapping
    public ResponseEntity<String> upload(
            @RequestParam MultipartFile image
    ) {

        String imageUrl = uploadService.uploadImage(image);

        return ResponseEntity.ok(imageUrl);
    }
}