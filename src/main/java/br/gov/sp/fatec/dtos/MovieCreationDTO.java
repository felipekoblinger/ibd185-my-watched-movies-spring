package br.gov.sp.fatec.dtos;

import br.gov.sp.fatec.enums.MovieType;
import br.gov.sp.fatec.models.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
public class MovieCreationDTO {
    /* Validations */
    @NotEmpty
    private String title;

    /* Json */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    /* Validations */
    @NotNull
    @PastOrPresent
    private LocalDate date;

    /* Validations */
    @NotNull
    @Min(0)
    @Max(5)
    private Integer rating;

    /* Validations */
    @NotNull
    private MovieType type;

    /* Validations */
    @NotEmpty
    private String imdbId;

    /* Validations */
    @NotEmpty
    private String theMovieDatabaseId;

    /* Json */
    @JsonIgnore
    private Account account;
}
