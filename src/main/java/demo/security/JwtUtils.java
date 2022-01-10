package demo.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.function.Function;

@Service
public class JwtUtils {
    private final static String SECRET = "pda123";

    public String extractUsername(String token) {
        return extractClaim(token, Claims::getSubject);
    }

    public Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }

    private Claims extractAllClaims(String token) {
        return Jwts.parser().
                setSigningKey(SECRET).
                parseClaimsJws(token).
                getBody();
    }


    private Boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    public String doGenerateRefreshToken(UserDetails user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (24 * 60 * 60 * 1000L)))
                .signWith(SignatureAlgorithm.HS512, SECRET).compact();
    }

    public String generateToken(UserDetails user) {
        Map<String, Object> claims = new HashMap<>();
        Collection<? extends GrantedAuthority> roles = user.getAuthorities();
        if (roles.contains(new SimpleGrantedAuthority("ROLE_ADMIN"))) {
            claims.put("isAdmin", true);
        }
        if (roles.contains(new SimpleGrantedAuthority("ROLE_USER"))) {
            claims.put("isUser", true);
        }
        return accessToken(claims, user);
    }

    private String accessToken(Map<String, Object> claims, UserDetails user) {
        Date now = new Date();
        return Jwts.builder()
                .setSubject(user.getUsername())
                .setIssuedAt(now)
                .setExpiration(new Date(now.getTime() + (2*60 * 100L)))
                .addClaims(claims)
                .signWith(SignatureAlgorithm.HS512, SECRET).
                compact();
    }


    public List<SimpleGrantedAuthority> getRolesFromToken(String token) {
        Claims claims = extractAllClaims(token);
        claims.get("role", Boolean.class);
        List<SimpleGrantedAuthority> roles = new ArrayList<>();
        Boolean isAdmin = claims.get("isAdmin", Boolean.class);
        Boolean isUser = claims.get("isUser", Boolean.class);

        if (isAdmin != null && isAdmin) {
            roles.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        }

        if (isUser != null && isUser) {
            roles.add(new SimpleGrantedAuthority("ROLE_USER"));
        }
        return roles;
    }


    public Boolean validateToken(String token, UserDetails userDetails) {
        final String username = extractUsername(token);
        return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
    }
}
