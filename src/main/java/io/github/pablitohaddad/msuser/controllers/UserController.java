package io.github.pablitohaddad.msuser.controllers;

import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.security.LoginDTO;
import io.github.pablitohaddad.msuser.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Get all", description = "hello")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Create user", description = "Create User")
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO createDto){
        UserResponseDTO user = userService.createUser(createDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

//    @Operation(summary = "User login", description = "User login")
//    @PostMapping
//    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO login){
//        return null;
//    }

}