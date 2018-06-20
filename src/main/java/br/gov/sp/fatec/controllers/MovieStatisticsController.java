package br.gov.sp.fatec.controllers;

import br.gov.sp.fatec.security.models.SecurityAccount;
import br.gov.sp.fatec.services.MovieStatisticsService;
import br.gov.sp.fatec.views.View;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/movies-statistics/")
public class MovieStatisticsController {
    private final MovieStatisticsService movieStatisticsService;

    @Autowired
    public MovieStatisticsController(MovieStatisticsService movieStatisticsService) {
        this.movieStatisticsService = movieStatisticsService;
    }

    @RequestMapping(value = "/overall", method = RequestMethod.GET)
    @JsonView(View.Common.class)
    public ResponseEntity<ObjectNode> overall() {
        SecurityAccount securityAccount = (SecurityAccount) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal();
        ObjectNode response = new ObjectMapper().createObjectNode();
        response.put("total", movieStatisticsService.numberOfMovies(securityAccount.getId()));

        ArrayNode arrayNode = response.putArray("monthly");
        List<ObjectNode> objectNodes = movieStatisticsService.numberOfMoviesMonthly(securityAccount.getId());

        for (ObjectNode objectNode: objectNodes) {
            arrayNode.add(objectNode);
        }
        response.set("monthly", arrayNode);

        response.set("type", movieStatisticsService.numberOfMoviesType(securityAccount.getId()));
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
