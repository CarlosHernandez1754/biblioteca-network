package com.biblioteca.autor.service;

import com.biblioteca.autor.model.Autor;
import com.biblioteca.autor.repository.AutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class AutorService {

    private final AutorRepository autorRepository;

    @Autowired
    public AutorService(AutorRepository autorRepository) {
        this.autorRepository = autorRepository;
    }

    public Flux<Autor> findAll() {
        return autorRepository.findAll();
    }

    public Mono<Autor> findById(String id) {
        return autorRepository.findById(id);
    }

    public Mono<Autor> save(Autor autor) {
        return autorRepository.save(autor);
    }

    public Mono<Void> deleteById(String id) {
        return autorRepository.deleteById(id);
    }
}