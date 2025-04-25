package com.biblioteca.autor.controller;

import com.biblioteca.autor.model.Autor;
import com.biblioteca.autor.service.AutorService;
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

@WebFluxTest(controllers = AutorController.class)
class AutorControllerTest {

    @MockBean
    private AutorService autorService;

    @Autowired
    private WebTestClient webTestClient;

    private Autor autor;

    @BeforeEach
    void setUp() {
        autor = new Autor("Miguel de Cervantes", "Español");
        autor.setId("1");
    }

    @Test
    void getAllAutoresTest() {
        when(autorService.findAll()).thenReturn(Flux.just(autor));

        webTestClient.get()
                .uri("/autores")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Autor.class)
                .hasSize(1)
                .contains(autor);
    }

    @Test
    void getAutorByIdTest() {
        when(autorService.findById(anyString())).thenReturn(Mono.just(autor));

        webTestClient.get()
                .uri("/autores/1")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Autor.class)
                .isEqualTo(autor);
    }

    @Test
    void getAutorByIdNotFoundTest() {
        when(autorService.findById(anyString())).thenReturn(Mono.empty());

        webTestClient.get()
                .uri("/autores/999")
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isNotFound();
    }

    @Test
    void createAutorTest() {
        when(autorService.save(any(Autor.class))).thenReturn(Mono.just(autor));

        webTestClient.post()
                .uri("/autores")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(autor)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Autor.class)
                .isEqualTo(autor);
    }

    @Test
    void updateAutorTest() {
        Autor autorActualizado = new Autor("Miguel de Cervantes Saavedra", "Español");
        autorActualizado.setId("1");
        
        when(autorService.findById(anyString())).thenReturn(Mono.just(autor));
        when(autorService.save(any(Autor.class))).thenReturn(Mono.just(autorActualizado));

        webTestClient.put()
                .uri("/autores/1")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(autorActualizado)
                .exchange()
                .expectStatus().isOk()
                .expectBody(Autor.class)
                .isEqualTo(autorActualizado);
    }

    @Test
    void deleteAutorTest() {
        when(autorService.deleteById(anyString())).thenReturn(Mono.empty());

        webTestClient.delete()
                .uri("/autores/1")
                .exchange()
                .expectStatus().isNoContent();
    }
}