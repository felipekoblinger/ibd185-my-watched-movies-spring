package br.gov.sp.fatec.services;

import br.gov.sp.fatec.utils.JsonIO;
import com.google.gson.JsonObject;

import org.springframework.stereotype.Service;

@Service("theMovieDatabaseService")
public class TheMovieDatabaseServiceImpl implements TheMovieDatabaseService {
    @Override
    public JsonObject getMovieById(String id) {
        String url = String.format("https://api.themoviedb.org" +
                "/3/movie/%s?api_key=21bf4ea44600c1fd5d93194cbfd6fd71", id);

        return new JsonIO().load(url);
    }

    @Override
    public JsonObject searchMoviesByTerm(String term) {
        String url = String.format("https://api.themoviedb" +
                ".org/3/search/movie?api_key=21bf4ea44600c1fd5d93194cbfd6fd71&query=%s", term);
        return new JsonIO().load(url);
    }
}
