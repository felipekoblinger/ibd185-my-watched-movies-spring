package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Movie;
import br.gov.sp.fatec.repositories.MovieRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import javax.validation.Validator;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@Service("movieService")
public class MovieServiceImpl implements MovieService {
    private final MovieRepository movieRepository;
    private final Validator validator;

    @Autowired
    public MovieServiceImpl(MovieRepository movieRepository, Validator validator) {
        this.movieRepository = movieRepository;
        this.validator = validator;
    }

    @Override
    @Transactional
    public void create(Movie movie) {
        movie.setCreatedAt(LocalDateTime.now());
        movie.setUpdatedAt(LocalDateTime.now());
        movieRepository.save(movie);

        /* Validate failed */
        Set<ConstraintViolation<Movie>> constraintViolations = validator.validate(movie);
        if (!constraintViolations.isEmpty()) {
            throw new ConstraintViolationException(constraintViolations);
        }
    }

    @Override
    public Movie findById(Long id) {
        return movieRepository.findById(id).orElse(null);
    }

    @Override
    public Movie findByUuidAndAccountId(String uuid, Long accountId) {
        return movieRepository.findByUuidAndAccountId(uuid, accountId);
    }

    @Override
    public List<Movie> findAllByAccountId(Long id) {
        return movieRepository.findAllByAccountId(id);
    }

    @Override
    @Transactional
    public void update(Movie movie) {
        movie.setUpdatedAt(LocalDateTime.now());
        movieRepository.save(movie);
    }

    @Override
    @Transactional
    public void delete(Movie movie) {
        movieRepository.delete(movie);
    }
}
