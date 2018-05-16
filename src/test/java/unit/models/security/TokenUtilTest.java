package unit.models.security;

import br.gov.sp.fatec.security.TokenUtil;
import br.gov.sp.fatec.security.models.SecurityAccount;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.JwtException;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNotSame;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class TokenUtilTest {
    private static final String TEST_USER = "testUser";

    @Mock
    private Clock clock;

    @InjectMocks
    private TokenUtil tokenUtil;

    @Before
    public void init() {
        MockitoAnnotations.initMocks(this);

        ReflectionTestUtils.setField(tokenUtil, "expiration", 3600L);
        ReflectionTestUtils.setField(tokenUtil, "secret", "unknown");
    }

    @Test
    public void testGetUsernameFromToken() {
        when(clock.now()).thenReturn(new Date());
        final String token = createToken();
        assertEquals("Username from token must be the same", TEST_USER, tokenUtil.getUsernameFromToken(token));
    }


    @Test
    public void testGenerateToken() {
        when(clock.now()).thenReturn(new Date());
        String token = createToken();
        assertNotNull("Token generated cannot be null", token);
    }

    @Test
    public void testCanTokenBeRefreshed() {
        // false - false
        when(clock.now()).thenReturn(new Date(new Date().getTime() + 100000));
        String token = createToken();
        assertTrue("Token must be validated", tokenUtil.canTokenBeRefreshed(token, new Date()));
    }

    @Test
    public void testCannotTokenBeRefreshed() {
        String token;
        when(clock.now()).thenReturn(new Date(new Date().getTime() - 1000));
        token = createToken();
        assertFalse("Token must be validated", tokenUtil.canTokenBeRefreshed(token, new Date()));
    }

    @Test(expected = JwtException.class)
    public void testCannotTokenBeRefreshedWithExpiredToken() {
        String token;
        when(clock.now()).thenReturn(new Date(new Date().getTime() - 86400000));
        token = createToken();
        tokenUtil.canTokenBeRefreshed(token, new Date());
    }

    @Test
    public void testRefreshToken() {
        when(clock.now()).thenReturn(new Date());
        String token = createToken();
        String tokenRefreshed = tokenUtil.refreshToken(token);
        assertNotSame("Tokens must be different", token, tokenRefreshed);
    }

    @Test
    public void testValidateToken() {
        when(clock.now()).thenReturn(new Date());
        SecurityAccount securityAccount = new SecurityAccount();
        securityAccount.setUsername(TEST_USER);

        String token = tokenUtil.generateToken(securityAccount);
        assertTrue("Token must be validated", tokenUtil.validateToken(token, securityAccount));
    }

    @Test
    public void testValidateTokenWithLastResetPasswordExpired() {
        when(clock.now()).thenReturn(new Date());
        SecurityAccount securityAccount = new SecurityAccount();
        securityAccount.setUsername(TEST_USER);
        securityAccount.setLastPasswordResetDate(new Date(new Date().getTime() + 86400000));

        String token = tokenUtil.generateToken(securityAccount);
        assertFalse("Token cannot be validated", tokenUtil.validateToken(token, securityAccount));
    }

    @Test
    public void testValidateTokenWithDifferentUsername() {
        when(clock.now()).thenReturn(new Date());
        SecurityAccount securityAccount = new SecurityAccount();
        securityAccount.setUsername(TEST_USER);
        String token = tokenUtil.generateToken(securityAccount);

        securityAccount.setUsername("anotherTestUser");
        assertFalse("Token cannot be validated", tokenUtil.validateToken(token, securityAccount));
    }

    @Test(expected = JwtException.class)
    public void testValidateTokenWithExpiredToken() {
        when(clock.now()).thenReturn(new Date(new Date().getTime() - 86400000));
        SecurityAccount securityAccount = new SecurityAccount();
        securityAccount.setUsername(TEST_USER);

        String token = tokenUtil.generateToken(securityAccount);
        tokenUtil.validateToken(token, securityAccount);
    }

    private String createToken() {
        SecurityAccount securityAccount = new SecurityAccount();
        securityAccount.setUsername(TEST_USER);
        return tokenUtil.generateToken(securityAccount);
    }
}
