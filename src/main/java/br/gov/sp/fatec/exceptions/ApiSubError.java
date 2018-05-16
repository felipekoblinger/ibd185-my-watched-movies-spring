package br.gov.sp.fatec.exceptions;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiSubError {
    private String object;
    private String field;
    private Object rejectedValue;
    private String message;

    ApiSubError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
