package io.github.pablitohaddad.msuser.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CPF;
import org.springframework.format.annotation.DateTimeFormat;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserCreateDTO {

    @Size(min = 3, message = "First name must be at least 3 characters long")
    private String firstName;

    @Size(min = 3, message = "Last mame must be at least 3 characters long")
    private String lastName;

    @CPF
    @Size(min = 14, max = 14)
    private String cpf;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Email // aceita nome@dominio.com
    private String email;

    @Size(min = 9, max = 9)
    private String cep;

    @Size(min = 6)
    private String password;

    private Boolean active;
}
