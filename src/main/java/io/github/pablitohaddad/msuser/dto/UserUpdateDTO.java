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

import java.time.LocalDate;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class UserUpdateDTO {

    @Size(min = 3, message = "First name must be at least 3 characters long")
    private String firstName;

    @Size(min = 3, message = "Last mame must be at least 3 characters long")
    private String lastName;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    @JsonFormat(pattern = "yyyy-MM-dd")
    private Date birthdate;

    @Email
    private String email;

    @Size(min = 9, max = 9)
    private String cep;

    public UserUpdateDTO(String firstName, String lastName, LocalDate birthdate, String email, String cep){}
}
