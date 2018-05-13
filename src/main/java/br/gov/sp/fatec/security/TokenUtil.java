package br.gov.sp.fatec.security;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import br.gov.sp.fatec.security.models.SecurityAccount;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

@Component
@PropertySource(value = { "classpath:application.properties" })
public class TokenUtil {
    private final Log log = LogFactory.getLog(this.getClass());

    @Value("${MWM_TOKEN_SECRET:unknown}")
    private String secret;

    /* Default Expiration: 1 day */
    @Value("${MWM_TOKEN_EXPIRATION:86400}")
    private Long expiration;

    @Value("created")
    private String created;

    public String getUsernameFromToken(String token) {
        try {
            final Claims claims = this.getClaimsFromToken(token);
            if (claims != null)
                return claims.getSubject();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private Date getCreatedDateFromToken(String token) {
        try {
            final Claims claims = this.getClaimsFromToken(token);
            if (claims != null) {
                return new Date((Long) claims.get(this.created));
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private Date getExpirationDateFromToken(String token) {
        try {
            final Claims claims = this.getClaimsFromToken(token);
            if (claims != null) {
                return claims.getExpiration();
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(this.secret)
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    private Date generateCurrentDate() {
        return new Date(System.currentTimeMillis());
    }

    private Date generateExpirationDate() {
        return new Date(System.currentTimeMillis() + this.expiration * 1000);
    }

    private Boolean isTokenExpired(String token) {
        final Date expirationDateFromToken = this.getExpirationDateFromToken(token);
        if (expirationDateFromToken != null) {
            return expirationDateFromToken.before(this.generateCurrentDate());
        }
        return true;
    }

    private Boolean isCreatedBeforeLastPasswordReset(Date created, Date lastPasswordReset) {
        return (lastPasswordReset != null && created.before(lastPasswordReset));
    }

    public String generateToken(UserDetails userDetails) {
        Map<String, Object> claims = new HashMap<>();
        claims.put("sub", userDetails.getUsername());
        claims.put(this.created, this.generateCurrentDate());
        return this.generateToken(claims);
    }

    private String generateToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setExpiration(this.generateExpirationDate())
                .signWith(SignatureAlgorithm.HS512, this.secret)
                .compact();
    }

    public Boolean canTokenBeRefreshed(String token, Date lastPasswordReset) {
        final Date createdDateFromToken = this.getCreatedDateFromToken(token);
        return (!(this.isCreatedBeforeLastPasswordReset(createdDateFromToken, lastPasswordReset))
                && (!(this.isTokenExpired(token))));
    }

    public String refreshToken(String token) {
        try {
            final Claims claims = this.getClaimsFromToken(token);
            if (claims != null) {
                claims.put(this.created, this.generateCurrentDate());
                return this.generateToken(claims);
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return null;
    }

    public Boolean validateToken(String token, UserDetails userDetails) {
        SecurityAccount user = (SecurityAccount) userDetails;
        final String usernameFromToken = this.getUsernameFromToken(token);
        final Date createdDateFromToken = this.getCreatedDateFromToken(token);
        return (usernameFromToken.equals(user.getUsername())
                && !(this.isTokenExpired(token))
                && !(this.isCreatedBeforeLastPasswordReset(createdDateFromToken, user.getLastPasswordReset())));
    }
}