package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.services.TheMovieDatabaseService;
import com.fasterxml.jackson.databind.JsonNode;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/the-movie-database/")
public class TheMovieDatabaseController {
    private final TheMovieDatabaseService theMovieDatabaseService;

    public TheMovieDatabaseController(TheMovieDatabaseService theMovieDatabaseService) {
        this.theMovieDatabaseService = theMovieDatabaseService;
    }

    @RequestMapping(value = "/search/", method = RequestMethod.GET)
    public ResponseEntity<JsonNode> searchMovies(@RequestParam String term) {
        return new ResponseEntity<>(theMovieDatabaseService.searchMoviesByTerm(term),
                                    HttpStatus.OK);
    }
}
