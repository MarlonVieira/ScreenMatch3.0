package br.com.alura.screenmatch.main;

import br.com.alura.screenmatch.model.SeasonData;
import br.com.alura.screenmatch.model.SeriesData;
import br.com.alura.screenmatch.service.ConsumptionAPI;
import br.com.alura.screenmatch.service.ConvertData;
import java.util.*;

public class MainScreenMatch {
    private Scanner scan = new Scanner(System.in);
    private ConsumptionAPI consumptionAPI = new ConsumptionAPI();
    private ConvertData convertData = new ConvertData();
    private List<SeasonData> seasons = new ArrayList<>();
    private final String URL = "https://www.omdbapi.com/?t=";
    private final String SEASON = "&season=";
    private final String API_KEY = "&apikey=6585022c";
    private List<SeriesData> listSeries = new ArrayList<>();

    public void showMenu() {
        var option = -1;

        while (option != 0) {
            var menu = """
                    1 - Search serie
                    2 - Search episodes
                    3 - List searched series
                    
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
        listSeries.add(data);
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
        SeriesData seriesData = getSeriesData();
        List<SeasonData> season = new ArrayList<>();

        for (int i = 1; i <= seriesData.totalSeasons(); i++) {
            var json = consumptionAPI.getData(URL + seriesData.title().replace(" ", "+") + "&season=" + i + API_KEY);
            SeasonData dadosTemporada = convertData.getData(json, SeasonData.class);
            season.add(dadosTemporada);
        }
        season.forEach(System.out::println);
    }

    private void listSearchedSeries() {
        listSeries.forEach(System.out::println);
    }
}