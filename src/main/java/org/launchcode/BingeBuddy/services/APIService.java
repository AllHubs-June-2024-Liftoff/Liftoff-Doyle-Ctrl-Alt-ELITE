package org.launchcode.BingeBuddy.services;

import org.launchcode.BingeBuddy.config.APIConfiguration;
import org.launchcode.BingeBuddy.models.Movie;
import org.launchcode.BingeBuddy.models.SearchResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class APIService {

    @Autowired
    private APIConfiguration apiConfig;

    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;

    public Movie fetchMovie(String apiId) {
        RestTemplate restTemplate = new RestTemplate();
        String url = apiUrl + "?apikey=" + apiKey + "&id=" + apiId;
        return restTemplate.getForObject(url, Movie.class);
    }

    public List<Movie> searchMoviesByTitle(String title) {
        RestTemplate restTemplate = new RestTemplate();
        String url = String.format("%s?apikey=%s&s=%s",
                apiConfig.getApiUrl(),
                apiConfig.getApiKey(),
                title);

        try {
            // Assuming OMDb API's search response is mapped to a custom SearchResponse class
            SearchResponse response = restTemplate.getForObject(url, SearchResponse.class);
            return response != null ? response.getMovies() : List.of();
        } catch (Exception e) {
            throw new RuntimeException("Failed to search movies by title", e);
        }
    }


}
