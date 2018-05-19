package factories;

import br.gov.sp.fatec.enums.Gender;
import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.models.Authority;
import br.gov.sp.fatec.models.AuthorityName;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class AccountFactory {
    public static Account validResource() {
        Account account = new Account();
        account.setId(1L);
        account.setUuid("b9aaacdf-a45e-44a8-982e-5c6adf80729f");
        account.setUsername("marionakani");
        account.setEmail("marionakani@test.com");
        account.setName("Mario Nakani");
        account.setPassword("$2a$10$DxyylUA0byYKKhbkSM7RWu/jkzm9fxjLS4FkMz/yoj6xCGtA.Znje");
        account.setGender(Gender.MALE);
        account.setBirthday(LocalDate.of(1990, 1, 20));

        List<Authority> authorities = new ArrayList<>();
        Authority authority = new Authority();
        authority.setName(AuthorityName.ROLE_COMMON);
        authorities.add(authority);
        account.setAuthorities(authorities);

        return account;
    }
}
