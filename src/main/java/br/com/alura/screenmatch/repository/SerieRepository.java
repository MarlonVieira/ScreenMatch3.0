package br.com.alura.screenmatch.repository;

import br.com.alura.screenmatch.model.Category;
import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.Serie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SerieRepository extends JpaRepository<Serie, Long> {
    Optional<Serie> findByTitleContainingIgnoreCase(String serieName);
    List<Serie> findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(String actorName, Double rating);
    List<Serie> findTop5ByOrderByRatingDesc();
    List<Serie> findByGenre(Category category);
    //List<Serie> findBytotalSeasonsLessThanEqualAndRatingGreaterThanEqual(Integer maxSeasons, Double rating);
    @Query("select s from Serie s WHERE s.totalSeasons <= :maxSeasons AND s.rating >= :rating")
    List<Serie> seriesBySeasonsAndRating(Integer maxSeasons, Double rating);

    @Query("select e from Serie s JOIN s.episodeList e WHERE e.title ILIKE %:excerptEpisode%")
    List<Episode> episodesByExcerpt(String excerptEpisode);

    @Query("select e from Serie s JOIN s.episodeList e WHERE s = :serie ORDER BY e.rating DESC LIMIT 5")
    List<Episode> topEpisodesBySerie(Serie serie);

    @Query("select e from Serie s JOIN s.episodeList e WHERE s = :serie AND YEAR(e.releaseDate) >= :releaseYear")
    List<Episode> episodesOfSerieByYear(Serie serie, String releaseYear);
}