package unit.models;

import br.gov.sp.fatec.models.Account;
import br.gov.sp.fatec.models.Authority;
import br.gov.sp.fatec.models.AuthorityName;
import factories.AccountFactory;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.ValidationUtil;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AccountTest {
    private ValidationUtil validationUtil = new ValidationUtil();
    private Account account;

    @Before
    public void setUp() {
        Locale.setDefault(new Locale("en", "EN"));
        this.account = AccountFactory.validResource();
    }

    @Test
    public void testPojo() {
        final Class<?> classUnderTest = Account.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.SETTER)
                .areWellImplemented();
    }

    /* Validations */
    @Test
    public void testValidationAccount() {
        assertEquals(0, validationUtil.getErrorSize(account));
    }

    @Test
    public void testValidationId() {
        account.setId(null);
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be null", validationUtil.getErrorMessage(account));
    }

    @Test
    public void testValidationUuid() {
        account.setUuid(null);
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be empty", validationUtil.getErrorMessage(account));

        account.setUuid("");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be empty", validationUtil.getErrorMessage(account));
    }

    @Test
    public void testValidationUsername() {
        account.setUsername(null);
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be null", validationUtil.getErrorMessage(account));

        account.setUsername("");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(account));

        account.setUsername("12");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(account));

        account.setUsername("rawegOZethyiS4HtD14W\n" +
                "gOjvuEwvZL7Fh3tDeGCU\n" +
                "1OE948pohVKLtWeKQmwH\n" +
                "iiO1dIhJW201ob2ZwKez\n" +
                "Hfvsg0vaczB4etKrsqQE1");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(account));
    }

    @Test
    public void testValidationEmail() {
        account.setEmail(null);
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be empty", validationUtil.getErrorMessage(account));

        account.setEmail("");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be empty", validationUtil.getErrorMessage(account));

        account.setEmail("error@.");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must be a well-formed email address", validationUtil.getErrorMessage(account));
    }

    @Test
    public void testValidationPassword() {
        account.setPassword(null);
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be empty", validationUtil.getErrorMessage(account));

        account.setPassword("");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be empty", validationUtil.getErrorMessage(account));
    }

    @Test
    public void testValidationName() {
        account.setName(null);
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be null", validationUtil.getErrorMessage(account));

        account.setName("");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(account));

        account.setName("12");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(account));

        account.setName("rawegOZethyiS4HtD14W\n" +
                "gOjvuEwvZL7Fh3tDeGCU\n" +
                "1OE948pohVKLtWeKQmwH\n" +
                "iiO1dIhJW201ob2ZwKez\n" +
                "Hfvsg0vaczB4etKrsqQE1");
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(account));
    }

    @Test
    public void testValidationBirthday() {
        account.setBirthday(null);
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be null", validationUtil.getErrorMessage(account));

        account.setBirthday(LocalDate.now().plusYears(1));
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must be a past date", validationUtil.getErrorMessage(account));
    }

    @Test
    public void testValidationAuthorities() {
        account.setAuthorities(null);
        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("must not be null", validationUtil.getErrorMessage(account));

        List<Authority> authorities = new ArrayList<>();
        Authority roleCommon = new Authority();
        roleCommon.setName(AuthorityName.ROLE_COMMON);
        Authority rolePaid = new Authority();
        rolePaid.setName(AuthorityName.ROLE_PAID);
        authorities.add(roleCommon);
        authorities.add(rolePaid);
        account.setAuthorities(authorities);

        assertEquals(1, validationUtil.getErrorSize(account));
        assertEquals("size must be between 1 and 1", validationUtil.getErrorMessage(account));
    }
}