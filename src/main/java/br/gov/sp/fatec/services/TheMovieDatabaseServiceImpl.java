package br.gov.sp.fatec.services;

import br.gov.sp.fatec.utils.JsonIO;
import com.fasterxml.jackson.databind.JsonNode;

import org.springframework.stereotype.Service;

@Service("theMovieDatabaseService")
public class TheMovieDatabaseServiceImpl implements TheMovieDatabaseService {
    @Override
    public JsonNode getMovieById(String id) {
        String url = String.format("https://api.themoviedb.org" +
                "/3/movie/%s?api_key=21bf4ea44600c1fd5d93194cbfd6fd71", id);

        return new JsonIO().load(url);
    }

    @Override
    public JsonNode searchMoviesByTerm(String term) {
        String url = String.format("https://api.themoviedb" +
                ".org/3/search/movie?api_key=21bf4ea44600c1fd5d93194cbfd6fd71&query=%s", term);
        return new JsonIO().load(url);
    }
}
