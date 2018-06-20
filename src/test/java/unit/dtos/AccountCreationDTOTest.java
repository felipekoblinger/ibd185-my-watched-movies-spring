package unit.dtos;

import br.gov.sp.fatec.dtos.AccountCreationDTO;
import com.github.javafaker.Faker;
import factories.AccountCreationDTOFactory;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.ValidationUtil;

import java.time.LocalDate;
import java.util.Locale;

import static org.junit.Assert.assertEquals;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class AccountCreationDTOTest {
    private ValidationUtil validationUtil = new ValidationUtil();
    private AccountCreationDTO accountCreationDTO;

    @Before
    public void setUp() {
        Locale.setDefault(new Locale("en", "EN"));
        this.accountCreationDTO = AccountCreationDTOFactory.validResource();
    }

    @Test
    public void testPojo() {
        final Class<?> classUnderTest = AccountCreationDTO.class;
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER, Method.SETTER)
                .areWellImplemented();
    }

    /* Validations */
    @Test
    public void testValidationAccountCreationDTO() {
        assertEquals(0, validationUtil.getErrorSize(accountCreationDTO));
    }

    @Test
    public void testValidationUsername() {
        accountCreationDTO.setUsername(null);
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("must not be null", validationUtil.getErrorMessage(accountCreationDTO));

        accountCreationDTO.setUsername(Faker.instance().lorem().characters(2));
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(accountCreationDTO));

        accountCreationDTO.setUsername(Faker.instance().lorem().characters(101));
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(accountCreationDTO));
    }

    @Test
    public void testValidationEmail() {
        accountCreationDTO.setEmail(null);
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("must not be empty", validationUtil.getErrorMessage(accountCreationDTO));

        accountCreationDTO.setEmail("");
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("must not be empty", validationUtil.getErrorMessage(accountCreationDTO));

        accountCreationDTO.setEmail("email@err.");
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("must be a well-formed email address", validationUtil.getErrorMessage(accountCreationDTO));
    }

    @Test
    public void testValidationName() {
        accountCreationDTO.setName(null);
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("must not be null", validationUtil.getErrorMessage(accountCreationDTO));

        accountCreationDTO.setName(Faker.instance().lorem().characters(2));
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(accountCreationDTO));

        accountCreationDTO.setName(Faker.instance().lorem().characters(101));
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("size must be between 3 and 100", validationUtil.getErrorMessage(accountCreationDTO));
    }

    @Test
    public void testValidationPassword() {
        accountCreationDTO.setPassword(null);
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("must not be null", validationUtil.getErrorMessage(accountCreationDTO));

        accountCreationDTO.setPassword(Faker.instance().lorem().characters(7));
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("size must be between 8 and 100", validationUtil.getErrorMessage
                (accountCreationDTO));

        accountCreationDTO.setPassword(Faker.instance().lorem().characters(101));
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("size must be between 8 and 100", validationUtil.getErrorMessage
                (accountCreationDTO));
    }

    @Test
    public void testValidationBirthday() {
        accountCreationDTO.setBirthday(null);
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("must not be null", validationUtil.getErrorMessage(accountCreationDTO));

        accountCreationDTO.setBirthday(LocalDate.now().plusDays(5));
        assertEquals(1, validationUtil.getErrorSize(accountCreationDTO));
        assertEquals("must be a past date", validationUtil.getErrorMessage(accountCreationDTO));
    }
}
