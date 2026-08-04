package com.example.pokedex.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class UploadService {
    private final String UPLOAD_DIR = "uploads";

    public String uploadImage(MultipartFile file){


        try {
            // criar a pasta de uploads caso ela não exista
            Path uploadPath = Paths.get(UPLOAD_DIR);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // pega o nome
            String fileName = file.getOriginalFilename();

            // Remove possíveis caminhos enviados pelo navegador
            fileName = Paths.get(fileName).getFileName().toString();

            Path filePath = uploadPath.resolve(fileName);

            Files.copy(
                    file.getInputStream(),
                    filePath,
                    java.nio.file.StandardCopyOption.REPLACE_EXISTING
            );

            // retorna URL
            return "/uploads/" + fileName;

        } catch (Exception e) {
            throw new RuntimeException("Erro ao fazer upload da imagem");
        }
    }
}
