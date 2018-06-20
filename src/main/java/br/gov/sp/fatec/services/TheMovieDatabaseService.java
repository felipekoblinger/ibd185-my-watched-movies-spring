package br.gov.sp.fatec.services;

import com.fasterxml.jackson.databind.JsonNode;

public interface TheMovieDatabaseService {
    JsonNode getMovieById(String id);
    JsonNode searchMoviesByTerm(String term);
}
