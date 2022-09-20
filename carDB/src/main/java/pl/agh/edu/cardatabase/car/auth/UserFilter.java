package pl.agh.edu.cardatabase.car.auth;

import Auth.AccessData;
import Auth.AccountPrx;
import Auth.Role;
import Auth.TokenVerificationStatus;
import com.zeroc.Ice.Communicator;
import com.zeroc.Ice.ConnectionRefusedException;
import com.zeroc.Ice.ObjectPrx;
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
    public static final String INVALID_PROXY = "Invalid proxy";


    private final Communicator communicator;

    public UserFilter(final Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String userToken = request.getHeader(TOKEN_FIELD);
        final String userId = request.getHeader(USERID_FIELD);

        SecurityContextHolder.clearContext();
        if (userToken != null) {
            if (Objects.equals(request.getServletPath(), "/api/admin/cars") && request.getMethod().equals("POST")) {
                verify(userId, userToken, Role.Admin);
            } else {
                verify(userId, userToken, Role.User);
            }
        }
        filterChain.doFilter(request, response);
    }

    private void verify(final String userId, final String userToken, final Role role) {
        final ObjectPrx baseAccount = communicator.stringToProxy(constructString(userId));
        AccountPrx account;
        try {
            account = AccountPrx.checkedCast(baseAccount);
        } catch ( ConnectionRefusedException exception) {
            return;
        }
        if (account == null) {
            throw new Error(INVALID_PROXY);
        }

        if (account.verifyToken(new AccessData(userToken, role)) == TokenVerificationStatus.Ok) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(new User(Integer.valueOf(userId),
                            role.toString()), null, List.of()));
        }
    }

    private String constructString(final String userId) {
        return "account/" + userId + ":" + communicator.getProperties().getProperty("Account.Proxy");
    }
}
