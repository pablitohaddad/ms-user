package io.github.pablitohaddad.msuser.controllers;

import io.github.pablitohaddad.msuser.dto.PasswordUpdateDTO;
import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.UserUpdateDTO;
import io.github.pablitohaddad.msuser.exceptions.handler.ErrorMessage;
import io.github.pablitohaddad.msuser.mqueue.UserPublisher;
import io.github.pablitohaddad.msuser.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Create user", description = "End points by create user")
@RestController
@RequestMapping("/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final UserPublisher userPublisher;

    @Operation(summary = "Create a new user", description = "Feature to create a new user",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Resource created successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "409", description = "User already registered in the system",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid input data",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@Valid @RequestBody UserCreateDTO createDto){
        UserResponseDTO user = userService.createUser(createDto);
        return  ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
    @Operation(summary = "Retrieve a user by id", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource retrieved successfully",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class)))
            })
    @GetMapping("/{id}")
    public ResponseEntity<UserResponseDTO> getUserById(@PathVariable Long id){
        UserResponseDTO user = userService.getUserById(id);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
    @Operation(summary = "Update user by id", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource successfully updated",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}")
    public ResponseEntity<UserUpdateDTO> updateUser(@PathVariable Long id, @RequestBody @Valid UserUpdateDTO updatedUser){
        userService.updateUser(id, updatedUser);
        return ResponseEntity.ok().body(updatedUser);
    }
    @Operation(summary = "Update password by id", description = "No authentication required",
            responses = {
                    @ApiResponse(responseCode = "200", description = "Resource successfully updated",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = UserResponseDTO.class))),
                    @ApiResponse(responseCode = "404", description = "Resource not found",
                            content = @Content(mediaType = "application/json;charset=UTF-8", schema = @Schema(implementation = ErrorMessage.class))),
                    @ApiResponse(responseCode = "422", description = "Resource not processed due to invalid data",
                            content = @Content(mediaType = " application/json;charset=UTF-8",
                                    schema = @Schema(implementation = ErrorMessage.class)))
            })
    @PutMapping("/{id}/{oldPassword}")
    public ResponseEntity<Void> updatePassword(@PathVariable Long id, @PathVariable String oldPassword, @RequestBody @Valid PasswordUpdateDTO newPassword){
        userService.updatePassword(id, oldPassword, newPassword);
        return ResponseEntity.noContent().build();
    }

}
