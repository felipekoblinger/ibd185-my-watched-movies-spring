package unit.models.security;

import br.gov.sp.fatec.security.models.SecurityAccount;
import org.junit.Test;
import pl.pojo.tester.api.assertion.Method;

import static junit.framework.TestCase.assertTrue;
import static pl.pojo.tester.api.assertion.Assertions.assertPojoMethodsFor;

public class SecurityAccountTest {
    @Test
    public void testPojo() {
        final Class<?> classUnderTest = SecurityAccount.class;

        assertPojoMethodsFor(classUnderTest)
                .testing(Method.GETTER)
                .areWellImplemented();
        assertPojoMethodsFor(classUnderTest)
                .testing(Method.SETTER)
                .areWellImplemented();
    }

    @Test
    public void testIsAccountNonExpired() {
        SecurityAccount securityAccount = new SecurityAccount();
        assertTrue("Default value of isAccountNonExpired() is true",
                securityAccount.isAccountNonExpired());
    }

    @Test
    public void testIsAccountNonLocked() {
        SecurityAccount securityAccount = new SecurityAccount();
        assertTrue("Default value of isAccountNonLocked() is true",
                securityAccount.isAccountNonLocked());
    }

    @Test
    public void testIsCredentialsNonExpired() {
        SecurityAccount securityAccount = new SecurityAccount();
        assertTrue("Default value of isCredentialsNonExpired() is true",
                securityAccount.isCredentialsNonExpired());
    }

    @Test
    public void testIsEnabled() {
        SecurityAccount securityAccount = new SecurityAccount();
        assertTrue("Default value of isEnabled() is true",
                securityAccount.isEnabled());
    }
}
