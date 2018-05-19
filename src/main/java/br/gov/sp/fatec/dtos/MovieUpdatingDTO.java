package br.gov.sp.fatec.dtos;

import br.gov.sp.fatec.models.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@Getter
@Setter
public class MovieUpdatingDTO {
    private String title;

    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    @PastOrPresent
    private LocalDate date;

    private String imdbId;

    private String theMovieDatabaseId;

    @JsonIgnore
    private Account account;
}