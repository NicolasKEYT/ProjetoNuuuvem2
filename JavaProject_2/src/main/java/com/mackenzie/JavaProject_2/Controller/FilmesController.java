package com.mackenzie.JavaProject_2.Controller;

import com.mackenzie.JavaProject_2.Model.Filmes;
import com.mackenzie.JavaProject_2.Repository.FilmesRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/filmes")
public class FilmesController {

    private final FilmesRepository repository;

    public FilmesController(FilmesRepository repository) {
        this.repository = repository;
    }

    // 1 - Listar todos os filmes
    @GetMapping
    public List<Filmes> listarTodos() {
        return repository.findAll();
    }

    // 2 - Buscar um filme aleatório salvo no banco
    @GetMapping("/aleatorio")
    public Filmes buscarFilmeAleatorio() {
        List<Filmes> todos = repository.findAll();
        if (todos.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Nenhum filme encontrado");
        }
        int indexAleatorio = new Random().nextInt(todos.size());
        return todos.get(indexAleatorio);
    }

    // 3 - Atualizar imdbRating de um filme
    @PutMapping("/{id}")
    public Filmes atualizarImdbRating(@PathVariable Long id, @RequestParam String imdbRating) {
        Filmes filme = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado"));
        try {
            Double ratingDouble = Double.parseDouble(imdbRating);
            filme.setImdbRating(String.valueOf(ratingDouble));
        } catch (NumberFormatException e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "imdbRating inválido");
        }
        return repository.save(filme);
    }

    // 4 - Deletar um filme pelo id
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deletarFilme(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Filme não encontrado");
        }
        repository.deleteById(id);
    }
}