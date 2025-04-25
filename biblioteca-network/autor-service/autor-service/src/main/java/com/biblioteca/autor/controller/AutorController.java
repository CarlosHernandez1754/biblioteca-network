package com.biblioteca.autor.controller;

import com.biblioteca.autor.model.Autor;
import com.biblioteca.autor.service.AutorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/autores")
public class AutorController {

    private final AutorService autorService;

    @Autowired
    public AutorController(AutorService autorService) {
        this.autorService = autorService;
    }

    @GetMapping
    public Flux<Autor> getAllAutores() {
        return autorService.findAll();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<Autor>> getAutorById(@PathVariable String id) {
        return autorService.findById(id)
                .map(autor -> ResponseEntity.ok(autor))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Autor> createAutor(@RequestBody Autor autor) {
        return autorService.save(autor);
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<Autor>> updateAutor(@PathVariable String id, 
                                                  @RequestBody Autor autor) {
        return autorService.findById(id)
                .flatMap(existingAutor -> {
                    existingAutor.setNombre(autor.getNombre());
                    existingAutor.setNacionalidad(autor.getNacionalidad());
                    existingAutor.actualizarFecha();
                    return autorService.save(existingAutor);
                })
                .map(updatedAutor -> ResponseEntity.ok(updatedAutor))
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> deleteAutor(@PathVariable String id) {
        return autorService.deleteById(id)
                .then(Mono.just(new ResponseEntity<Void>(HttpStatus.NO_CONTENT)))
                .defaultIfEmpty(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}