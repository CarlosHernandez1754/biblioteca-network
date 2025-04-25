package com.biblioteca.libro.controller;

import com.biblioteca.libro.model.Libro;
import com.biblioteca.libro.service.LibroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/libros")
public class LibroController {

    private final LibroService libroService;

    @Autowired
    public LibroController(LibroService libroService) {
        this.libroService = libroService;
    }

    @GetMapping
    public Flux<Libro> getAllLibros() {
        return libroService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Libro>> getLibroById(@PathVariable String id) {
        return libroService.findById(id)
                .map(libro -> ResponseEntity.ok(libro))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Libro> createLibro(@RequestBody Libro libro) {
        return libroService.save(libro);
    }

    @PutMapping("/{id}/autor")
    public Mono<ResponseEntity<Libro>> asignarAutor(@PathVariable String id, 
                                                   @RequestParam String autorId) {
        return libroService.asignarAutor(id, autorId)
                .map(libro -> ResponseEntity.ok(libro))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteLibro(@PathVariable String id) {
        return libroService.deleteById(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
