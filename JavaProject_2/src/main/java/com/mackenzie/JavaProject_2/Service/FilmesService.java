package com.mackenzie.JavaProject_2.Service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.mackenzie.JavaProject_2.Model.Filmes;
import com.mackenzie.JavaProject_2.Repository.FilmesRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

@Service
public class FilmesService {

    private final String apiKey = "86b8d744";
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;
    private final FilmesRepository repository;

    public FilmesService(FilmesRepository repository) {
        this.restTemplate = new RestTemplate();
        this.objectMapper = new ObjectMapper();
        this.repository = repository;
    }

    // Método que importa e salva filmes populares no banco
    public void importarFilmesPopulares() {
        // Exemplo: buscar filmes com palavra "Batman"
        List<Filmes> filmes = buscarFilmesNoOmdb("war");
        repository.saveAll(filmes);
    }

    public List<Filmes> buscarFilmesNoOmdb(String titulo) {
        List<Filmes> filmesEncontrados = new ArrayList<>();

        try {
            // Monta a URL para buscar filmes pelo título (busca geral)
            // Usando 's' para search que retorna uma lista
            String url = String.format("http://www.omdbapi.com/?apikey=%s&s=%s", apiKey, titulo);

            String jsonResponse = restTemplate.getForObject(url, String.class);
            JsonNode root = objectMapper.readTree(jsonResponse);

            if (root.has("Search")) {
                JsonNode searchResults = root.get("Search");

                for (JsonNode node : searchResults) {
                    // Para cada resultado, buscar detalhes completos (para pegar avaliação e descrição)
                    String imdbID = node.get("imdbID").asText();

                    Filmes filmeDetalhado = buscarDetalhesFilme(imdbID);
                    if (filmeDetalhado != null) {
                        filmesEncontrados.add(filmeDetalhado);
                    }
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return filmesEncontrados;
    }

    private Filmes buscarDetalhesFilme(String imdbID) {
        try {
            String urlDetalhes = String.format("http://www.omdbapi.com/?apikey=%s&i=%s&plot=short", apiKey, imdbID);
            String jsonDetalhes = restTemplate.getForObject(urlDetalhes, String.class);

            return objectMapper.readValue(jsonDetalhes, Filmes.class);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}