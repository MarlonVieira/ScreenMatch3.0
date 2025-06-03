package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Serie;
import jakarta.persistence.Id;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SerieRepository extends JpaRepository<Serie, Long> {
}
