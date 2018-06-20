package br.gov.sp.fatec.services;

import br.gov.sp.fatec.utils.JsonIO;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service("theMovieDatabaseService")
public class TheMovieDatabaseServiceImpl implements TheMovieDatabaseService {

    @Value("${THE_MOVIE_DB_API_KEY}")
    private String apiKey;

    @Override
    public JsonNode getMovieById(String id) {
        String url = String.format("https://api.themoviedb.org" +
                "/3/movie/%s?api_key=%s", id, this.apiKey);
        return new JsonIO().load(url);
    }

    @Override
    public JsonNode searchMoviesByTerm(String term) {
        String url = String.format("https://api.themoviedb" +
                ".org/3/search/movie?api_key=%s&query=%s", this.apiKey, term);
        return new JsonIO().load(url);
    }
}
