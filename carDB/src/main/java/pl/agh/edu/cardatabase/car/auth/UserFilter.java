package pl.agh.edu.cardatabase.car.auth;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

@Component
public class UserFilter extends OncePerRequestFilter {
    public static final String TOKEN_FIELD = "token";
    public static final String USERID_FIELD = "userId";


    public UserFilter() {
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String userToken = request.getHeader(TOKEN_FIELD);
        final String userId = request.getHeader(USERID_FIELD);

        SecurityContextHolder.clearContext();
        System.out.println(request.getMethod());
        if (userToken != null) {
            if (Objects.equals(request.getServletPath(), "/api/cars") && request.getMethod().equals("POST")) {
                verifyAdmin(userId, userToken);
            } else {
                verifyUser(userId, userToken);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void verifyUser(final String userId, final String userToken) {
        System.out.println("USER VERIFYING");
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new User(Integer.valueOf(userId), "user"), null, List.of()));
    }

    private void verifyAdmin(final String userId, final String userToken) {
        System.out.println("ADMIN VERIFYING");
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new User(Integer.valueOf(userId), "admin"), null, List.of()));

    }
}
