package org.launchcode.BingeBuddy.services;

import org.launchcode.BingeBuddy.models.Movie;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class APIService {

    @Value("${external.api.url}")
    private String apiUrl;

    @Value("${external.api.key}")
    private String apiKey;

    public Movie fetchMovie(String apiId){
       RestTemplate restTemplate = new RestTemplate();
       String url = apiUrl + "?apikey=" + apiKey + "&id=" + apiId;
       return restTemplate.getForObject(url, Movie.class);
    }
}
