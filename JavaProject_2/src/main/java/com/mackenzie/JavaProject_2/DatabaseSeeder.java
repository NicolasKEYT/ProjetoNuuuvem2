package com.mackenzie.JavaProject_2;

import com.mackenzie.JavaProject_2.Service.FilmesService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

//Componente que será executado ao iniciar a aplicação
@Component
public class DatabaseSeeder implements CommandLineRunner {

    private final FilmesService filmesService;

    //Injeção de dependência do FilmesService
    public DatabaseSeeder(FilmesService filmesService) {
        this.filmesService = filmesService;
    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("Importando filmes populares no banco...");
        filmesService.importarFilmesPopulares();
        System.out.println("Importação concluída!");
    }
}
