package br.gov.sp.fatec.services;

import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.security.access.prepost.PreAuthorize;

import java.util.List;

public interface MovieStatisticsService {
    @PreAuthorize("hasRole('ROLE_PAID')")
    Long numberOfMovies(Long accountId);

    @PreAuthorize("hasRole('ROLE_PAID')")
    List<ObjectNode> numberOfMoviesMonthly(Long accountId);

    @PreAuthorize("hasRole('ROLE_PAID')")
    ObjectNode numberOfMoviesType(Long accountId);
}
