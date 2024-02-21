package io.github.pablitohaddad.msuser.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.validator.constraints.br.CPF;

import java.util.Date;

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
}
