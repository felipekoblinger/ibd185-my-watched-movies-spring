package br.gov.sp.fatec.services;

import br.gov.sp.fatec.models.Movie;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface MovieService {
    @PreAuthorize("hasAnyRole('ROLE_COMMON', 'ROLE_PAID')")
    void create(Movie movie);

    @PreAuthorize("hasAnyRole('ROLE_COMMON', 'ROLE_PAID')")
    Movie findById(Long id);

    @PreAuthorize("hasAnyRole('ROLE_COMMON', 'ROLE_PAID')")
    Movie findByUuidAndAccountId(String uuid, Long accountId);

    @PreAuthorize("hasAnyRole('ROLE_COMMON', 'ROLE_PAID')")
    List<Movie> findAllByAccountId(Long id);

    @PreAuthorize("hasAnyRole('ROLE_COMMON', 'ROLE_PAID')")
    void update(Movie movie);

    @PreAuthorize("hasAnyRole('ROLE_COMMON', 'ROLE_PAID')")
    void delete(Movie movie);
}
