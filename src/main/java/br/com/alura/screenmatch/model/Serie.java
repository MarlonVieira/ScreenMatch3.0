package br.com.alura.screenmatch.model;

import br.com.alura.screenmatch.service.ConsultChatGPT;

import java.util.OptionalDouble;

public class Serie {
    private String title;
    private Integer totalSeasons;
    private Double rating;
    private String year;
    private Category genre;
    private String actors;
    private String poster;
    private String synopsis;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTotalSeasons() {
        return totalSeasons;
    }

    public void setTotalSeasons(Integer totalSeasons) {
        this.totalSeasons = totalSeasons;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public Category getGenre() {
        return genre;
    }

    public void setGenre(Category genre) {
        this.genre = genre;
    }

    public String getActors() {
        return actors;
    }

    public void setActors(String actors) {
        this.actors = actors;
    }

    public String getPoster() {
        return poster;
    }

    public void setPoster(String poster) {
        this.poster = poster;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    @Override
    public String toString() {
        return  "genre=" + genre +
                ", title='" + title + '\'' +
                ", totalSeasons=" + totalSeasons +
                ", rating=" + rating +
                ", year=" + year +
                ", actors='" + actors + '\'' +
                ", poster='" + poster + '\'' +
                ", synopsis='" + synopsis + '\'';
    }

    public Serie(SeriesData seriesData) {
        this.title = seriesData.title();
        this.totalSeasons = seriesData.totalSeasons();
        this.rating = OptionalDouble.of(Double.valueOf(seriesData.rating())).orElse(0);
        this.year = seriesData.year();
        this.genre = Category.fromString(seriesData.genre().split(",")[0].trim());
        this.actors = seriesData.actors();
        this.poster = seriesData.poster();
        this.synopsis = ConsultChatGPT.getTraduction(seriesData.synopsis()).trim();
    }
}