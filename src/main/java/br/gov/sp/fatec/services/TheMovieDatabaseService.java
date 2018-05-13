package br.gov.sp.fatec.services;

import com.google.gson.JsonObject;

public interface TheMovieDatabaseService {
    JsonObject getMovieById(String id);
    JsonObject searchMoviesByTerm(String term);
}
