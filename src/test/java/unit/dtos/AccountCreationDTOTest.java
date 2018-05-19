package unit.dtos;

import br.gov.sp.fatec.dtos.AccountCreationDTO;
import factories.AccountCreationDTOFactory;
import org.junit.Before;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;
import utils.ValidationUtil;

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
                .testing(Method.GETTER)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.SETTER)
                .areWellImplemented();
    }

    /* Validations */
    @Test
    public void testValidationAccountCreationDTO() {
        assertEquals(0, validationUtil.getErrorSize(accountCreationDTO));
    }

    @Test
    public void testValidationUsername() {

    }

    @Test
    public void testValidationEmail() {

    }

    @Test
    public void testValidationName() {

    }

    @Test
    public void testValidationPassword() {

    }

    @Test
    public void testValidationBirthday() {

    }
}