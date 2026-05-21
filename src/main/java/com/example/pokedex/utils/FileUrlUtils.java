package com.example.pokedex.utils;

import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

// normalizar o caminho da imagem, para ela sempre retornar a url exemplo http/localhost
// usar esse metodo no mapper da service para nromalziar a url da imagem do dto
// com isso a url fica salva no banco sem dominio mas na hora do get adciona o dominio
// serve localmente e em produção pois ele pega o dominio atual
public class FileUrlUtils {
    public static String toPublicUrl(String path) {
        if (path == null) return null;

        return ServletUriComponentsBuilder
                .fromCurrentContextPath()
                .path(path.startsWith("/") ? path : "/" + path)
                .toUriString();
    }
}
