package sinhan.server1.global.security;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.*;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import sinhan.server1.global.security.dto.FamilyInfoResponse;
import sinhan.server1.global.security.dto.JwtTokenResponse;
import sinhan.server1.global.security.dto.UserInfoResponse;
import sinhan.server1.global.security.secret.Secret;
import sinhan.server1.global.utils.exception.AuthException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

@Service
@AllArgsConstructor
public class JwtService {

    private static final long ACCESS_TOKEN_EXPIRATION_TIME = 1800 * 1000; // 30 minutes
    private static final long REFRESH_TOKEN_EXPIRATION_TIME = 3 * 3600 * 1000; // 3 hours
    private static final String TOKEN_TYPE = "JWT";
    private ObjectMapper objectMapper;

    private String createToken(long sn, List<FamilyInfoResponse> familyInfo, long expirationTime) {
        Date now = new Date();
        return Jwts.builder().header().add("typ", TOKEN_TYPE).and().claim("sn", sn).claim("familyInfo", familyInfo).encodePayload(true).issuedAt(now).expiration(new Date(System.currentTimeMillis() + expirationTime)).signWith(Secret.getJwtKey(), SignatureAlgorithm.HS256).compact();
    }

    public String createAccessToken(long serialNumber, List<FamilyInfoResponse> familyInfo) {
        return createToken(serialNumber, familyInfo, ACCESS_TOKEN_EXPIRATION_TIME);
    }

    public String createRefreshToken(long serialNumber) {
        return createToken(serialNumber, new ArrayList<>(), REFRESH_TOKEN_EXPIRATION_TIME);
    }

    public String getAccessToken() throws ExpiredJwtException, NullPointerException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Authorization").substring(7);
    }

    public String getRefreshToken() throws ExpiredJwtException, NullPointerException {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
        return request.getHeader("Refresh-Token");
    }

    public UserInfoResponse getUserInfo(String token) throws AuthException, ExpiredJwtException {
        // 1. JWT 추출
        if (token == null || token.isEmpty()) {
            throw new AuthException("EMPTY_JWT");
        }

        // 2. JWT parsing
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(Secret.getJwtKey())
                .build()
                .parseSignedClaims(token);

        // 3. userInfo 추출
        return getUserInfoFromClaims(claims);
    }

    public UserInfoResponse getUserInfo() throws AuthException, ExpiredJwtException {
        String token = getAccessToken();
        if (token == null || token.isEmpty()) {
            throw new AuthException("EMPTY_JWT");
        }

        // 2. JWT parsing
        Jws<Claims> claims = Jwts.parser()
                .verifyWith(Secret.getJwtKey())
                .build()
                .parseSignedClaims(token);

        // 3. userInfo 추출
        return getUserInfoFromClaims(claims);
    }

    private UserInfoResponse getUserInfoFromClaims(Jws<Claims> claims) throws AuthException {
        String jwtType = claims.getHeader().getType();
        if (jwtType == null || (!jwtType.equals(TOKEN_TYPE))) {
            throw new AuthException("NOT_JWT");
        }

        long sn = claims.getPayload().get("sn", Long.class);

        TypeReference<List<FamilyInfoResponse>> typeFamilyInfo = new TypeReference<List<FamilyInfoResponse>>() {
        };
        List<FamilyInfoResponse> familyInfo = objectMapper.convertValue(claims.getPayload().get("familyInfo"), typeFamilyInfo);

        return new UserInfoResponse(sn, familyInfo);
    }

    public Authentication getAuthentication(String token) throws AuthException, ExpiredJwtException {
        UserInfoResponse userInfo = getUserInfo(token);
        SimpleGrantedAuthority authority = new SimpleGrantedAuthority("ROLE_USER");

        return new UsernamePasswordAuthenticationToken(userInfo.getSn(), null, Collections.singletonList(authority));
    }

    public void sendJwtToken(HttpServletResponse response, JwtTokenResponse jwtTokenResponse) {
        response.setHeader("Authorization", "Bearer " + jwtTokenResponse.getAccessToken());
        response.setHeader("Refresh-Token", jwtTokenResponse.getRefreshToken());
    }

    public void sendJwtToken(HttpServletResponse response){
        response.setHeader("Authorization", getAccessToken());
        response.setHeader("Refresh-Token", getRefreshToken());
    }

    public void sendAccessToken(HttpServletResponse httpServletResponse, String accessToken) {
        httpServletResponse.setHeader("Authorization", "Bearer " + accessToken);
    }

    public boolean isTokenExpired(String token) {
        try {
            Claims claims = Jwts.parser()
                    .verifyWith(Secret.getJwtKey())
                    .build()
                    .parseSignedClaims(token)
                    .getPayload();
            Date expiration = claims.getExpiration();
            return expiration.before(new Date());
        } catch (ExpiredJwtException | MalformedJwtException | SignatureException | IllegalArgumentException e) {
            return true;
        }
    }
}