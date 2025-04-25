package com.biblioteca.autor.repository;

import com.biblioteca.autor.model.Autor;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AutorRepository extends ReactiveMongoRepository<Autor, String> {
    // Los métodos básicos ya están implementados por ReactiveMongoRepository
}