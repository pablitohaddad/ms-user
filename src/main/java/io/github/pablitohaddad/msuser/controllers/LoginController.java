package io.github.pablitohaddad.msuser.controllers;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.pablitohaddad.msuser.dto.security.LoginDTO;
import io.github.pablitohaddad.msuser.entities.UserNotification;
import io.github.pablitohaddad.msuser.enums.Events;
import io.github.pablitohaddad.msuser.exceptions.handler.ErrorMessage;
import io.github.pablitohaddad.msuser.jwt.JwtToken;
import io.github.pablitohaddad.msuser.jwt.JwtUserDetailsService;
import io.github.pablitohaddad.msuser.mqueue.UserPublisher;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@Slf4j
@RequestMapping("v1/login")
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final JwtUserDetailsService detailsService;
    private final AuthenticationManager authenticationManager;
    private final UserPublisher userPublisher;

    @PostMapping
    public ResponseEntity<?> login(@RequestBody @Valid LoginDTO dto, HttpServletRequest request) {
        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
            authenticationManager.authenticate(authenticationToken);
            JwtToken token = detailsService.getTokenAuthenticated(dto.getEmail());
            UserNotification userNotification = new UserNotification(dto.getEmail(), Events.LOGIN, LocalDate.now().toString());
            userPublisher.sendNotification(userNotification);
            return ResponseEntity.ok(token);
        } catch (AuthenticationException ex) {
            log.warn("Bad credentials from email {}", dto.getEmail());
            return ResponseEntity
                    .badRequest()
                    .body(new ErrorMessage(request, HttpStatus.BAD_REQUEST, "Invalid credentials"));
        } catch (JsonProcessingException ex) {
            log.warn("Error processing notification");
            return ResponseEntity
                    .status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorMessage(request, HttpStatus.INTERNAL_SERVER_ERROR, "Error processing notification"));
        }
    }
}
