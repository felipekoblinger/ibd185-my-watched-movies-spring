package br.gov.sp.fatec.dtos;

import br.gov.sp.fatec.enums.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Getter
@Setter
public class AccountCreationDTO {

    /* Validations */
    @NotEmpty
    private String username;

    /* Validations */
    @NotEmpty
    @Email
    private String email;

    /* Validations */
    @NotNull
    @Size(min = 3, max = 100)
    private String name;

    /* Validations */
    @NotNull
    @Size(min = 8, max = 100)
    private String password;

    /* Database */
    @Column(name = "gender", nullable = false)
    /* Validations */
    @NotNull
    private Gender gender;

    /* Json */
    @JsonSerialize(using = LocalDateSerializer.class)
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy")
    /* Validations */
    @NotNull
    @Past
    private LocalDate birthday;
}
