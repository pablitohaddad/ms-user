package io.github.pablitohaddad.msuser.dto.security;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor @ToString
public class LoginDTO {
    @Email
    private String email;
    @Size(min = 6)
    private String password;

}
