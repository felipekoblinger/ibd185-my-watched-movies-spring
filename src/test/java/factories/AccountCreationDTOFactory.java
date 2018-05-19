package factories;

import br.gov.sp.fatec.dtos.AccountCreationDTO;
import br.gov.sp.fatec.enums.Gender;
import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.models.Authority;
import br.gov.sp.fatec.models.AuthorityName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountCreationDTOFactory {
    public static AccountCreationDTO validResource() {
        AccountCreationDTO accountCreationDTO = new AccountCreationDTO();
        accountCreationDTO.setUsername("marionakani");
        accountCreationDTO.setEmail("marionakani@test.com");
        accountCreationDTO.setName("Mario Nakani");
        accountCreationDTO.setPassword("mario12345");
        accountCreationDTO.setGender(Gender.MALE);
        accountCreationDTO.setBirthday(LocalDate.of(1990, 1, 20));
        return accountCreationDTO;
    }
}
