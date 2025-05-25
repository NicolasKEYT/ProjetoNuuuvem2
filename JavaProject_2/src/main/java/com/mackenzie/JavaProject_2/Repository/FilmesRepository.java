package com.mackenzie.JavaProject_2.Repository;

import com.mackenzie.JavaProject_2.Model.Filmes;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FilmesRepository extends JpaRepository<Filmes, Long> {
}
