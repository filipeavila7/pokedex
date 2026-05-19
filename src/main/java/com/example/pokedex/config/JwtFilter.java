package com.example.pokedex.config;

import com.example.pokedex.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
/*
Request chega
Pega header Authorization
Extrai token
Extrai email
Busca usuário no banco
Valida token
Cria autenticação
Coloca usuário no Spring Security
Continua request
*/

// filtro para pegar o token em cada requisição
@Component
@AllArgsConstructor
public class JwtFilter extends OncePerRequestFilter { // Garante que esse filtro roda 1 vez por request
    private final JwtService jwtService;
    private final UserDetailsService userDetailsService;


    @Override // metodo que vai rodar em toda requisição
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization"); // pega a header autorization -> onde vai o token

        // Se não tiver token -> libera request, serve para login ou cadastro
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }
        // remover o bearer
        String token = authHeader.substring(7);
        // extrair email do token
        String email = jwtService.extractEmail(token);

        // Verifica se ainda não está autenticado
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            // busca usuario no banco
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            // valida o token
            if (jwtService.isValid(token, userDetails)) {

                // Cria autenticação no Spring
                UsernamePasswordAuthenticationToken auth =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null, // credentials → null (não precisa senha)
                                userDetails.getAuthorities()
                        );
                // Coloca usuário no contexto do Spring
                SecurityContextHolder.getContext().setAuthentication(auth);
            }
        }
        // Libera a request para controller normalmente
        filterChain.doFilter(request, response);
    }

}
