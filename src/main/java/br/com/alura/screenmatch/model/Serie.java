package br.com.alura.screenmatch.model;

import com.fasterxml.jackson.annotation.JsonAlias;

import java.util.OptionalDouble;
import java.util.OptionalInt;

public class Serie {
    private String title;
    private Integer totalSeasons;
    private Double rating;
    private Integer year;
    private Category genre;
    private String actors;
    private String poster;
    private String synopsis;

    public Serie(SeriesData seriesData) {
        this.title = seriesData.title();
        this.totalSeasons = seriesData.totalSeasons();
        this.rating = OptionalDouble.of(Double.valueOf(seriesData.rating())).orElse(0);
        this.year = OptionalInt.of(Integer.valueOf(seriesData.year().split("-")[0])).orElse(0);
        this.genre = Category.fromString(seriesData.genre().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.poster = seriesData.poster();
        this.synopsis = seriesData.synopsis();
    }
}