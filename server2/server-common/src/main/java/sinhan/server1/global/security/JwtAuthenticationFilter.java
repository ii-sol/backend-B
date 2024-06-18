package sinhan.server1.global.security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final JwtService jwtService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = null;
        try {
            token = jwtService.getAccessToken();
        } catch (NullPointerException e) {
            token = null;
        }

        Authentication authentication = null;
        if (token != null) {
            try {
                if (!jwtService.isTokenExpired(token)) {
                    authentication = jwtService.getAuthentication(token);
                }
            } catch (Exception e) {
                handleJwtServiceException(e, response);
            }
        }

        SecurityContextHolder.getContext().setAuthentication(authentication);

        filterChain.doFilter(request, response);
    }

    private void handleJwtServiceException(Exception exception, HttpServletResponse response) throws IOException, IOException {
        response.getWriter().write(exception.getMessage());
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
    }
}