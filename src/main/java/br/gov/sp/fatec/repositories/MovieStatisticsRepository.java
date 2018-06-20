package br.gov.sp.fatec.repositories;

import br.gov.sp.fatec.dtos.MovieGroupMonthlyDTO;
import br.gov.sp.fatec.dtos.MovieGroupTypeDTO;
import br.gov.sp.fatec.models.Movie;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MovieStatisticsRepository extends CrudRepository<Movie, Long> {

    @Query("SELECT COUNT(m) FROM Movie m WHERE m.accountId = :accountId")
    Long numberOfMovies(@Param("accountId") Long accountId);

    /* PostgreSQL query ONLY */
    @Query("SELECT NEW br.gov.sp.fatec.dtos.MovieGroupMonthlyDTO(" +
            "function('to_char', m.date, 'fmMonth YYYY')," +
            "function('to_char', m.date, 'YYYYMM')," +
            "COUNT(m)" +
            ")" +
            "FROM Movie m " +
            "WHERE m.accountId = :accountId " +
            "GROUP BY " +
            "function('to_char', m.date, 'fmMonth YYYY'), function('to_char', m.date, 'YYYYMM')" +
            "ORDER BY function('to_char', m.date, 'YYYYMM') ASC")
    List<MovieGroupMonthlyDTO> findAllGroupedMonthly(@Param("accountId") Long accountId);

    @Query("SELECT NEW br.gov.sp" +
            ".fatec.dtos.MovieGroupTypeDTO(m.type, COUNT(m))" +
            "FROM Movie m WHERE m.accountId = :accountId GROUP BY m.type")
    List<MovieGroupTypeDTO> findAllGroupedType(@Param("accountId") Long accountId);
}