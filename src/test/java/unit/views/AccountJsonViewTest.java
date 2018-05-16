package unit.views;

import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.models.Movie;
import br.gov.sp.fatec.views.View;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.Map;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

public class AccountJsonViewTest {
    @Test
    public void testDeserializationWithCommon() throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.disable(MapperFeature.DEFAULT_VIEW_INCLUSION);

        Account account = new Account();
        account.setUsername(" Accountprimary ");
        account.setName(" Account Primary ");
        account.setEmail("accountprimary@test.com");
        account.setBirthday(LocalDate.of(1990, 2, 20));

        account.setId(1L);
        account.setPassword("12345678");
        account.setMovies(new ArrayList<>());
        account.setLastPasswordResetDate(new Date());
        account.setAuthorities(new ArrayList<>());

        String accountJsonString = objectMapper.writerWithView(View.Common.class)
                .writeValueAsString(account);
        Map<String, Object> accountJson = objectMapper.readValue(accountJsonString, new TypeReference<Map<String,Object>>(){});
        assertNotNull("username must not be null", accountJson.get("username"));
        assertNotNull("email must not be null", accountJson.get("email"));
        assertNotNull("name must not be null", accountJson.get("name"));
        assertNotNull("birthday must not be null", accountJson.get("birthday"));

        assertNull("id must be null", accountJson.get("id"));
        assertNull("password must be null", accountJson.get("password"));
        assertNull("lastPasswordResetDate must be null", accountJson.get("lastPasswordResetDate"));
        assertNull("movies must be null", accountJson.get("movies"));
        assertNull("authorities must be null", accountJson.get("authorities"));
    }
}
