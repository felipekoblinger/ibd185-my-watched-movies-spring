package br.gov.sp.fatec.dtos;

import br.gov.sp.fatec.enums.MovieType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovieGroupTypeDTO {
    private MovieType type;
    private Long total;
}
