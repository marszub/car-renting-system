//All of this copied from Rental
package pl.edu.agh.tarrif.auth;

import Auth.AccessData;
import Auth.AccountPrx;
import Auth.Role;
import Auth.TokenVerificationStatus;
import com.zeroc.Ice.Communicator;
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

@Component
public class UserFilter extends OncePerRequestFilter {
    public static final String TOKEN_FIELD = "token";
    public static final String USERID_FIELD = "userId";

    private final Communicator communicator;

    public UserFilter(Communicator communicator) {
        this.communicator = communicator;
    }

    @Override
    protected void doFilterInternal(final HttpServletRequest request, final HttpServletResponse response,
                                    final FilterChain filterChain) throws ServletException, IOException {
        final String userToken = request.getHeader(TOKEN_FIELD);
        final String userId = request.getHeader(USERID_FIELD);

        SecurityContextHolder.clearContext();
        if (userToken != null) {
            verifyUser(userId, userToken);
        }
        filterChain.doFilter(request, response);
    }

    private void verifyUser(final String userId, final String userToken) {
        ObjectPrx baseAccount = communicator.stringToProxy("account/" + userId + ":" + communicator.getProperties().getProperty("Account.Proxy"));
        AccountPrx account = AccountPrx.checkedCast(baseAccount);
        if (account == null) throw new Error("Invalid proxy");

        Role role = Role.User;
        if(account.verifyToken(new AccessData(userToken, role)) == TokenVerificationStatus.Ok) {
            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(new User(Integer.valueOf(userId), Role.User.toString()), null, List.of()));
        }
    }
}
