package com.biblioteca.gateway.controller;

import com.biblioteca.gateway.config.JwtConfig;
import com.biblioteca.gateway.model.AuthRequest;
import com.biblioteca.gateway.model.AuthResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private JwtConfig jwtConfig;

    @PostMapping("/login")
    public Mono<ResponseEntity<AuthResponse>> login(@RequestBody AuthRequest request) {
        // En un caso real, validaríamos contra base de datos
        // Para simplificar, aceptamos cualquier usuario con contraseña "password"
        if ("password".equals(request.getPassword())) {
            String token = jwtConfig.generateToken(request.getUsername());
            return Mono.just(ResponseEntity.ok(new AuthResponse(token)));
        }
        return Mono.just(ResponseEntity.badRequest().build());
    }
}