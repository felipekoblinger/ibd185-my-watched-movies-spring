package br.gov.sp.fatec.exceptions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ApiSubError {
    @JsonIgnore
    private String object;
    
    private String field;
    private Object rejectedValue;
    private String message;

    ApiSubError(String object, String message) {
        this.object = object;
        this.message = message;
    }
}
