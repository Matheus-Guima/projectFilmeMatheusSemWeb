package br.com.matheus.filmesMatheus.repository;

import br.com.matheus.filmesMatheus.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
