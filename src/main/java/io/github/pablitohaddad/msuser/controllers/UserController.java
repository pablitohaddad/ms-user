package io.github.pablitohaddad.msuser.controllers;

import io.github.pablitohaddad.msuser.dto.PasswordUpdateDTO;
import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.UserUpdateDTO;
import io.github.pablitohaddad.msuser.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Creating and Editing Users", description = "Creating and editing users")
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
    @Operation(summary = "Get user by id", description = "get user")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @Operation(summary = "Update user", description = "Update user")
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO updatedUser){
        userService.updateUser(id, updatedUser);
        return ResponseEntity.ok().body(updatedUser);
    }
    @Operation(summary = "Update password", description = "Update password")
    @PutMapping("/{id}/{oldPassword}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @PathVariable String oldPassword, @RequestBody @Valid PasswordUpdateDTO newPassword){
        userService.updatePassword(id, oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

}
