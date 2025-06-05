package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.Episode;
import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.Serie;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.repository.SerieRepository;
import br.com.alura.screenmatch.service.ConsumptionAPI;
import br.com.alura.screenmatch.service.ConvertData;

import java.util.*;
import java.util.stream.Collectors;

public class MainScreenMatch {
    private Scanner scan = new Scanner(System.in);
    private ConsumptionAPI consumptionAPI = new ConsumptionAPI();
    private ConvertData convertData = new ConvertData();
    private List<SeasonData> seasons = new ArrayList<>();
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String SEASON = "&season=";
    private final String API_KEY = "&apikey=6585022c";
    private List<SeriesData> listSeries = new ArrayList<>();
    private SerieRepository serieRepository;
    private List<Serie> series = new ArrayList<>();

    public MainScreenMatch(SerieRepository serieRepository) {
        this.serieRepository = serieRepository;
    }

    public void showMenu() {
        var option = -1;

        while (option != 0) {
            var menu = """
                    1 - Search serie
                    2 - Search episodes
                    3 - List searched series
                    4 - Search serie by title
                    5 - Search series by actor
                    
                    0 - Leave""";

            System.out.println(menu);
            option = scan.nextInt();

            scan.nextLine();

            switch (option) {
                case 1:
                    searchWebSerie();
                    break;
                case 2:
                    searchEpisodesSerie();
                case 3:
                    listSearchedSeries();
                    break;
                case 4:
                    searchSerieByTitle();
                case 5:
                    searchSeriesByActor();
                    break;
                case 0:
                    System.out.println("Leaving...");
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }

    private void searchWebSerie() {
        SeriesData data = getSeriesData();
        Serie serie = new Serie(data);
        serieRepository.save(serie);
        System.out.println(data);
    }

    private SeriesData getSeriesData() {
        System.out.print("Enter the name of the serie: ");
        var serieName = scan.nextLine();
        var json = consumptionAPI.getData(URL + serieName.replace(" ", "+") + API_KEY);
        SeriesData seriesData = convertData.getData(json, SeriesData.class);
        return seriesData;
    }

    private void searchEpisodesSerie() {
        listSearchedSeries();
        System.out.print("Enter the name of the serie: ");
        var nameSerie = scan.nextLine();

        Optional<Serie> serie = serieRepository.findByTitleContainingIgnoreCase(nameSerie);

        if (serie.isPresent()) {
            var serieFound = serie.get();
            List<SeasonData> season = new ArrayList<>();

            for (int i = 1; i <= serieFound.getTotalSeasons(); i++) {
                var json = consumptionAPI.getData(URL + serieFound.getTitle().replace(" ", "+") + "&season=" + i + API_KEY);
                SeasonData dadosTemporada = convertData.getData(json, SeasonData.class);
                season.add(dadosTemporada);
            }
            season.forEach(System.out::println);

            List<Episode> episodeList =  season.stream()
                                                    .flatMap(d -> d.episodes().stream()
                                                            .map(e -> new Episode(d.season(), e)))
                                                    .collect(Collectors.toList());

            serieFound.setEpisodeList(episodeList);
            serieRepository.save(serieFound);
        } else {
            System.out.println("Serie not found!");
        }
    }

    private void listSearchedSeries() {
        series = serieRepository.findAll();
        series.stream()
                .sorted(Comparator.comparing(Serie::getGenre))
                .forEach(System.out::println);
    }

    private void searchSerieByTitle() {
        System.out.print("Enter the name of the serie: ");
        String serieName = scan.nextLine();
        Optional<Serie> searchSerie = serieRepository.findByTitleContainingIgnoreCase(serieName);

        if (searchSerie.isPresent()) {
            System.out.println("Serie data: " + searchSerie.get());
        } else {
            System.out.println("Serie not found!");
        }
    }

    private void searchSeriesByActor() {
        System.out.print("Enter the name of the actor: ");
        String actorName = scan.nextLine();
        System.out.print("Enter the rating: ");
        Double rating = scan.nextDouble();
        List<Serie> searchSerie = serieRepository.findByActorsContainingIgnoreCaseAndRatingGreaterThanEqual(actorName, rating);
        System.out.println("Actor " + actorName + " series: ");
        searchSerie.forEach(s -> System.out.println(s.getTitle() + " rating: " + s.getRating()));

    }
}