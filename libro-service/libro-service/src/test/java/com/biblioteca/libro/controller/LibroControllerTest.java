package com.biblioteca.libro.controller;


import com.biblioteca.libro.model.Libro;
import com.biblioteca.libro.service.LibroService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

@WebFluxTest(controllers = LibroController.class)
class LibroControllerTest {

    @MockBean
    private LibroService libroService;

    @Autowired
    private WebTestClient webTestClient;

    private Libro libro;

    @BeforeEach
    void setUp() {
        libro = new Libro("El Quijote", "123456789", 1605);
        libro.setId("1");
    }

    @Test
    void getAllLibrosTest() {
        when(libroService.findAll()).thenReturn(Flux.just(libro));

        webTestClient.get()
                .uri("/libros")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Libro.class)
                .hasSize(1)
                .contains(libro);
    }

    @Test
    void getLibroByIdTest() {
        when(libroService.findById(anyString())).thenReturn(Mono.just(libro));

        webTestClient.get()
                .uri("/libros/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Libro.class)
                .isEqualTo(libro);
    }

    @Test
    void getLibroByIdNotFoundTest() {
        when(libroService.findById(anyString())).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/libros/999")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createLibroTest() {
        when(libroService.save(any(Libro.class))).thenReturn(Mono.just(libro));

        webTestClient.post()
                .uri("/libros")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(libro)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Libro.class)
                .isEqualTo(libro);
    }

    @Test
    void asignarAutorTest() {
        String autorId = "autor123";
        libro.setAutorId(autorId);
        
        when(libroService.asignarAutor(anyString(), anyString())).thenReturn(Mono.just(libro));

        webTestClient.put()
                .uri(uriBuilder -> uriBuilder
                        .path("/libros/1/autor")
                        .queryParam("autorId", autorId)
                        .build())
                .exchange()
                .expectStatus().isOk()
                .expectBody(Libro.class)
                .isEqualTo(libro);
    }

    @Test
    void deleteLibroTest() {
        when(libroService.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/libros/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}