package br.com.alura.screenmatch.model;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Episode {
    private Integer season;
    private String title;
    private Integer episode;
    private Double rating;
    private LocalDate releaseDate;

    public Episode(Integer season, EpisodesData episodesData) {
        this.season = season;
        this.title = episodesData.title();
        this.episode = episodesData.episode();

        try {
            this.rating = Double.valueOf(episodesData.rating());
        } catch (NumberFormatException e) {
            this.rating = 0.0;
        }

        try {
            this.releaseDate = LocalDate.parse(episodesData.releaseDate());
        } catch (DateTimeParseException ex) {
            this.releaseDate = null;
        }
    }

    public Integer getSeason() {
        return season;
    }

    public void setSeason(Integer season) {
        this.season = season;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getEpisode() {
        return episode;
    }

    public void setEpisode(Integer episode) {
        this.episode = episode;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(LocalDate releaseDate) {
        this.releaseDate = releaseDate;
    }

    @Override
    public String toString() {
        return "season=" + season +
               ", title='" + title + '\'' +
               ", episode=" + episode +
               ", rating=" + rating +
               ", releaseDate=" + releaseDate;
    }
}
