package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.dtos.MovieCreationDTO;
import br.gov.sp.fatec.dtos.MovieUpdatingDTO;
import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.models.Movie;
import br.gov.sp.fatec.security.models.SecurityAccount;
import br.gov.sp.fatec.services.MovieService;
import br.gov.sp.fatec.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/movies/")
public class MovieController {
    private final MovieService movieService;

    public MovieController(MovieService movieService) {
        this.movieService = movieService;
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    @JsonView(View.Common.class)
    public ResponseEntity<Movie> create(@Valid @RequestBody MovieCreationDTO movieCreationDTO) {
        SecurityAccount securityAccount = (SecurityAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        Account account = modelMapper.map(securityAccount, Account.class);
        movieCreationDTO.setAccount(account);

        Movie movie = modelMapper.map(movieCreationDTO, Movie.class);
        movieService.create(movie);
        return new ResponseEntity<>(movie, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.PUT)
    @JsonView(View.Common.class)
    public ResponseEntity<Movie> update(@PathVariable String uuid,
                                         @Valid @RequestBody MovieUpdatingDTO movieUpdatingDTO) {
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Movie movie = movieService.findByUuidAndAccountId(uuid, account.getId());
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
        modelMapper.getConfiguration().setSkipNullEnabled(true);
        modelMapper.map(movieUpdatingDTO, movie);

        movieService.update(movie);

        return new ResponseEntity<>(movie, HttpStatus.OK);
    }

    @RequestMapping(value = "/", method = RequestMethod.GET)
    @JsonView(View.Common.class)
    public ResponseEntity<List<Movie>> index() {
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return new ResponseEntity<>(movieService.findAllByAccountId(account.getId()), HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.GET)
    @JsonView(View.Common.class)
    public ResponseEntity<Movie> show(@PathVariable String uuid) {
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        return new ResponseEntity<>(movieService.findByUuidAndAccountId(uuid, account.getId()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/{uuid}", method = RequestMethod.DELETE)
    @JsonView(View.Common.class)
    public ResponseEntity<String> destroy(@PathVariable String uuid) {
        SecurityAccount account = (SecurityAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        Movie movie = movieService.findByUuidAndAccountId(uuid, account.getId());
        movieService.delete(movie);
        return new ResponseEntity<>("Resource deleted successfully.", HttpStatus.OK);
    }

}
