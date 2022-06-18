package pl.agh.edu.carRentingMicroservice.auth;

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

@Component
public class UserFilter extends OncePerRequestFilter {
    public static final String TOKEN_FIELD = "token";

    public UserFilter() { }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String userToken = request.getHeader(TOKEN_FIELD);
        SecurityContextHolder.clearContext();
        if (userToken != null) {
            verifyUser(userToken);
        }
        filterChain.doFilter(request, response);
    }

    private void verifyUser(final String userToken) {
        //TMP when there is no ICE resolvers in auth microservice
        final Integer userId = 1;
        final String userRole = "user";
        SecurityContextHolder.getContext().setAuthentication(
                new UsernamePasswordAuthenticationToken(new User(userId, userRole), null, List.of()));
    }
}
