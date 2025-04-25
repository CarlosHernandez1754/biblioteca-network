package com.biblioteca.libro.repository;

import com.biblioteca.libro.model.Libro;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibroRepository extends ReactiveMongoRepository<Libro, String> {
    // Los métodos básicos ya están implementados por ReactiveMongoRepository
    // findById, save, findAll, etc.
}
