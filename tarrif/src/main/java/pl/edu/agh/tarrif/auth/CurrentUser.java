//All of this copied from Rental
package pl.edu.agh.tarrif.auth;

import org.springframework.security.core.annotation.CurrentSecurityContext;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.PARAMETER })
@Retention(RetentionPolicy.RUNTIME)
@CurrentSecurityContext(expression = "authentication.principal")
public @interface CurrentUser { }
