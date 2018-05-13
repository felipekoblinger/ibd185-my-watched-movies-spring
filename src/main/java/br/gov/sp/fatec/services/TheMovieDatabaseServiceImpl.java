package br.gov.sp.fatec.services;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

@Service("theMovieDatabaseService")
public class TheMovieDatabaseServiceImpl implements TheMovieDatabaseService {
    private final Log log = LogFactory.getLog(this.getClass());

    @Override
    public JsonObject getMovieById(String id) {
        try {
            URL url = new URL(String.format("https://api.themoviedb.org" +
                    "/3/movie/%s?api_key=21bf4ea44600c1fd5d93194cbfd6fd71", id));
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser
                    .parse(new InputStreamReader((InputStream) request.getContent()));
            return jsonElement.getAsJsonObject();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }

    @Override
    public JsonObject searchMoviesByTerm(String term) {
        try {
            URL url = new URL(String.format("https://api.themoviedb" +
                    ".org/3/search/movie?api_key=21bf4ea44600c1fd5d93194cbfd6fd71&query=%s", term));
            URLConnection request = url.openConnection();
            request.connect();

            JsonParser jsonParser = new JsonParser();
            JsonElement jsonElement = jsonParser
                    .parse(new InputStreamReader((InputStream) request.getContent()));
            return jsonElement.getAsJsonObject();
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return null;
    }
}
