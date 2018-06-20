package br.gov.sp.fatec.dtos;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
public class AccountChangePasswordDTO {
    /* Validations */
    @NotNull
    private String currentPassword;

    /* Validations */
    @NotNull
    @Size(min = 8, max = 100)
    private String newPassword;
}
