package com.biblioteca.libro.service;


import com.biblioteca.libro.model.Libro;
import com.biblioteca.libro.repository.LibroRepository;
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

class LibroServiceTest {

    @Mock
    private LibroRepository libroRepository;

    @InjectMocks
    private LibroService libroService;

    private Libro libro;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        
        libro = new Libro("El Quijote", "123456789", 1605);
        libro.setId("1");
    }

    @Test
    void findAllTest() {
        when(libroRepository.findAll()).thenReturn(Flux.just(libro));

        Flux<Libro> result = libroService.findAll();

        StepVerifier.create(result)
                .expectNext(libro)
                .verifyComplete();
    }

    @Test
    void findByIdTest() {
        when(libroRepository.findById(anyString())).thenReturn(Mono.just(libro));

        Mono<Libro> result = libroService.findById("1");

        StepVerifier.create(result)
                .expectNext(libro)
                .verifyComplete();
    }

    @Test
    void saveTest() {
        when(libroRepository.save(any(Libro.class))).thenReturn(Mono.just(libro));

        Mono<Libro> result = libroService.save(libro);

        StepVerifier.create(result)
                .expectNext(libro)
                .verifyComplete();
    }

    @Test
    void asignarAutorTest() {
        String autorId = "autor123";
        
        when(libroRepository.findById(anyString())).thenReturn(Mono.just(libro));
        when(libroRepository.save(any(Libro.class))).thenAnswer(invocation -> {
            Libro libroActualizado = invocation.getArgument(0);
            return Mono.just(libroActualizado);
        });

        Mono<Libro> result = libroService.asignarAutor("1", autorId);

        StepVerifier.create(result)
                .expectNextMatches(l -> l.getAutorId().equals(autorId))
                .verifyComplete();
    }

    @Test
    void deleteByIdTest() {
        when(libroRepository.deleteById(anyString())).thenReturn(Mono.empty());

        Mono<Void> result = libroService.deleteById("1");

        StepVerifier.create(result)
                .verifyComplete();
    }
}