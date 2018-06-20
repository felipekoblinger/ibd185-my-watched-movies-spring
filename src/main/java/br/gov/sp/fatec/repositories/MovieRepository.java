package br.gov.sp.fatec.repositories;

import br.gov.sp.fatec.models.Movie;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MovieRepository extends CrudRepository<Movie, Long> {
    List<Movie> findAllByAccountId(Long id);
    List<Movie> findAllByAccountIdOrderByDateDescCreatedAtDesc(Long id);
    Movie findByUuidAndAccountId(String uuid, Long accountId);
}