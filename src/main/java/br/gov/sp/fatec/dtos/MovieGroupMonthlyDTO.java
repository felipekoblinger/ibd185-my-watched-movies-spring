package br.gov.sp.fatec.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class MovieGroupMonthlyDTO {
    private String yearMonth;
    private String yearMonthNumber;
    private Long total;
}
