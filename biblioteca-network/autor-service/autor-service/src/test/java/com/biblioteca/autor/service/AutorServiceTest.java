package com.biblioteca.autor.service;

import com.biblioteca.autor.model.Autor;
import com.biblioteca.autor.repository.AutorRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class AutorServiceTest {

    @Mock
    private AutorRepository autorRepository;

    @InjectMocks
    private AutorService autorService;

    private Autor autor;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        autor = new Autor("Miguel de Cervantes", "Español");
        autor.setId("1");
    }

    @Test
    void findAllTest() {
        when(autorRepository.findAll()).thenReturn(Flux.just(autor));

        Flux<Autor> result = autorService.findAll();

        StepVerifier.create(result)
                .expectNext(autor)
                .verifyComplete();
    }

    @Test
    void findByIdTest() {
        when(autorRepository.findById(anyString())).thenReturn(Mono.just(autor));

        Mono<Autor> result = autorService.findById("1");

        StepVerifier.create(result)
                .expectNext(autor)
                .verifyComplete();
    }

    @Test
    void saveTest() {
        when(autorRepository.save(any(Autor.class))).thenReturn(Mono.just(autor));

        Mono<Autor> result = autorService.save(autor);

        StepVerifier.create(result)
                .expectNext(autor)
                .verifyComplete();
    }

    @Test
    void deleteByIdTest() {
        when(autorRepository.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = autorService.deleteById("1");

        StepVerifier.create(result)
                .verifyComplete();
    }
}