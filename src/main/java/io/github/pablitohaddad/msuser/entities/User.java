package io.github.pablitohaddad.msuser.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.time.LocalDate;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last-name")
    private String lastName;

    @Column(nullable = false, unique = true)
    private String cpf; // xxx-xxx-xxx.xx

    @Column(name = "birthdate")
    private Date birthdate;

    @Column(name = "email", nullable = false, unique = true) // formato valido user@email.com
    private String email;

    @Column(name = "cep")
    private String cep;

    @Column(name = "password")// salvo como criptografia no bd
    private String password;

    @Column(name = "active")
    private Boolean active;

    public User(long id, String firstName, String lastName, String cpf, LocalDate date, String email,String cep, String password, boolean active) {
    }
    public User(String firstName, String lastName, String cpf, LocalDate date, String email, String password, boolean active) {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", cpf='" + cpf + '\'' +
                ", birthdate=" + birthdate +
                ", email='" + email + '\'' +
                ", cep='" + cep + '\'' +
                ", password='" + password + '\'' +
                ", active=" + active +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        User user = (User) o;
        return Objects.equals(id, user.id) && Objects.equals(firstName, user.firstName) && Objects.equals(lastName, user.lastName) && Objects.equals(cpf, user.cpf) && Objects.equals(birthdate, user.birthdate) && Objects.equals(email, user.email) && Objects.equals(cep, user.cep) && Objects.equals(password, user.password) && Objects.equals(active, user.active);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, cpf, birthdate, email, cep, password, active);
    }
}
