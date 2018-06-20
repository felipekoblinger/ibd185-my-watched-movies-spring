package br.gov.sp.fatec.services;

import br.gov.sp.fatec.dtos.MovieGroupMonthlyDTO;
import br.gov.sp.fatec.dtos.MovieGroupTypeDTO;
import br.gov.sp.fatec.repositories.MovieStatisticsRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service("movieStatisticsService")
public class MovieStatisticsServiceImpl implements MovieStatisticsService {
    private final MovieStatisticsRepository movieStatisticsRepository;

    @Autowired
    public MovieStatisticsServiceImpl(MovieStatisticsRepository movieStatisticsRepository) {
        this.movieStatisticsRepository = movieStatisticsRepository;
    }

    @Override
    public Long numberOfMovies(Long accountId) {
        return movieStatisticsRepository.numberOfMovies(accountId);
    }

    @Override
    public List<ObjectNode> numberOfMoviesMonthly(Long accountId) {
        List<MovieGroupMonthlyDTO> moviesGroup = movieStatisticsRepository.findAllGroupedMonthly(accountId);
        List<ObjectNode> monthlyMoviesNodes = new ArrayList<>();

        for (MovieGroupMonthlyDTO monthlyMovie: moviesGroup) {
            ObjectNode monthlyMoviesNode = new ObjectMapper().createObjectNode();
            monthlyMoviesNode.put("yearMonth", monthlyMovie.getYearMonth());
            monthlyMoviesNode.put("total", monthlyMovie.getTotal());
            monthlyMoviesNodes.add(monthlyMoviesNode);
        }
        return monthlyMoviesNodes;
    }

    @Override
    public ObjectNode numberOfMoviesType(Long accountId) {
        List<MovieGroupTypeDTO> moviesGroup = movieStatisticsRepository.findAllGroupedType(accountId);
        ObjectNode monthlyTypeNode = new ObjectMapper().createObjectNode();

        for (MovieGroupTypeDTO typeMovie: moviesGroup) {
            monthlyTypeNode.put(typeMovie.getType().toString(), typeMovie.getTotal());
        }
        return monthlyTypeNode;
    }
}
