package io.github.pablitohaddad.msuser.jwt;
import org.springframework.security.core.userdetails.User;
import java.util.Collections;
public class JwtUserDetails extends User {
    private io.github.pablitohaddad.msuser.entities.User user;
    public JwtUserDetails(io.github.pablitohaddad.msuser.entities.User user) {
        super(user.getEmail(), user.getPassword(), Collections.emptyList());
        this.user = user;
    }
}
