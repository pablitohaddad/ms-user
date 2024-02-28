package io.github.pablitohaddad.msuser.common;

import io.github.pablitohaddad.msuser.dto.PasswordUpdateDTO;
import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.UserUpdateDTO;
import io.github.pablitohaddad.msuser.entities.User;
import io.github.pablitohaddad.msuser.jwt.JwtToken;

import java.time.LocalDate;

public class UserConstants {
    public static final User USER = new User( "Pablo", "Haddad", "805.351.280-10", LocalDate.now(), "pablo@email.com", "78211-286", true);
    public static final User USER_WITH_ID = new User (1L,"Pablo", "Haddad", "805.351.280-10", LocalDate.now(), "pablo@email.com", "78211-286","12345678", true);
    public static final User INVALID_USER = new User ("", "", "", LocalDate.now(), "", "", false);
    public static final UserResponseDTO USER_RESPONSE_DTO = new UserResponseDTO (1L, "Pablo", "pablo@email.com");
    public static final UserCreateDTO USER_CREATE_DTO = new UserCreateDTO ("Pablo", "Haddad", "805.351.280-10", LocalDate.now(), "pablo@email.com", "78211-286", true);
    public static final UserCreateDTO INVALID_USER_CREATE_DTO = new UserCreateDTO ("", "", "", LocalDate.now().plusDays(1), "", "", false);
    public static final UserUpdateDTO USER_UPDATE_DTO = new UserUpdateDTO ("Sophia", "Haddad", LocalDate.now(), "sophia@email.com", "78211-286");
    public static final JwtToken VALID_TOKEN = new JwtToken("TOKEN");
    public static final JwtToken INVALID_TOKEN = new JwtToken("");
    public static final PasswordUpdateDTO NEW_PASSWORD = new PasswordUpdateDTO("1234567890");



}
