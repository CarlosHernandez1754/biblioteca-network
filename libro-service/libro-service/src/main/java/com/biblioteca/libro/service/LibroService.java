package com.biblioteca.libro.service;

import com.biblioteca.libro.model.Libro;
import com.biblioteca.libro.repository.LibroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class LibroService {

    private final LibroRepository libroRepository;

    @Autowired
    public LibroService(LibroRepository libroRepository) {
        this.libroRepository = libroRepository;
    }

    public Flux<Libro> findAll() {
        return libroRepository.findAll();
    }

    public Mono<Libro> findById(String id) {
        return libroRepository.findById(id);
    }

    public Mono<Libro> save(Libro libro) {
        return libroRepository.save(libro);
    }

    public Mono<Libro> asignarAutor(String libroId, String autorId) {
        return libroRepository.findById(libroId)
                .flatMap(libro -> {
                    libro.setAutorId(autorId);
                    libro.actualizarFecha();
                    return libroRepository.save(libro);
                });
    }

    public Mono<Void> deleteById(String id) {
        return libroRepository.deleteById(id);
    }
}
