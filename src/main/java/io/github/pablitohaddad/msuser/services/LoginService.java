//package io.github.pablitohaddad.msuser.services;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import io.github.pablitohaddad.msuser.dto.security.LoginDTO;
//import io.github.pablitohaddad.msuser.entities.UserNotification;
//import io.github.pablitohaddad.msuser.enums.Events;
//import io.github.pablitohaddad.msuser.jwt.JwtToken;
//import io.github.pablitohaddad.msuser.jwt.JwtUserDetailsService;
//import io.github.pablitohaddad.msuser.mqueue.UserPublisher;
//import jakarta.servlet.http.HttpServletRequest;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.authentication.AuthenticationManager;
//import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
//import org.springframework.security.core.AuthenticationException;
//import org.springframework.stereotype.Service;
//import org.springframework.web.bind.annotation.RestController;
//
//import java.time.LocalDate;
//
//@Service
//@RequiredArgsConstructor
//public class LoginService {
//    private final JwtUserDetailsService detailsService;
//    private final AuthenticationManager authenticationManager;
//    private final UserPublisher userPublisher;
//
//    public JwtToken login(LoginDTO dto, HttpServletRequest request) throws Exception {
//        try {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(dto.getEmail(), dto.getPassword());
//            authenticationManager.authenticate(authenticationToken);
//            detailsService.getTokenAuthenticated(dto.getEmail());
//        }catch (AuthenticationException ex){
//            throw new RuntimeException("Bad credentials");
//        }try{
//            UserNotification notification = new UserNotification(dto.getEmail(), Events.LOGIN, LocalDate.now().toString());
//            userPublisher.sendNotification(notification);
//        }catch (Exception exception){
//            throw new Exception("Create Missing");
//        }
//
//
//    }
//}
