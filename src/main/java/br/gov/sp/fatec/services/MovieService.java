package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Movie;

import java.util.List;

public interface MovieService {
    void create(Movie movie);
    Movie findById(Long id);
    List<Movie> findAllByAccountId(Long id);
    void update(Movie movie);
    void delete(Movie movie);
}
