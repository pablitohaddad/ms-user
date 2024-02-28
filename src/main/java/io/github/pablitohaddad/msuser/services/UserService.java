package io.github.pablitohaddad.msuser.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.pablitohaddad.msuser.consumer.UserAddressConsumer;
import io.github.pablitohaddad.msuser.dto.*;
import io.github.pablitohaddad.msuser.dto.mapper.UserMapper;
import io.github.pablitohaddad.msuser.entities.User;
import io.github.pablitohaddad.msuser.entities.UserNotification;
import io.github.pablitohaddad.msuser.enums.Events;
import io.github.pablitohaddad.msuser.exceptions.PasswordInvalidException;
import io.github.pablitohaddad.msuser.exceptions.UniqueViolationException;
import io.github.pablitohaddad.msuser.mqueue.UserPublisher;
import io.github.pablitohaddad.msuser.repositorys.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserPublisher userPublisher;
    private final UserAddressConsumer addressConsumer;
    @Transactional
    public UserResponseDTO createUser(UserCreateDTO newUser) {
        String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        if(userRepository.existsByEmail(newUser.getEmail())){
            throw new UniqueViolationException("Email already exists");
        }
        if(userRepository.existsByCpf(newUser.getCpf())){
            throw new UniqueViolationException("Cpf already exists");
        }
        try{
            UserNotification notificationUpdatePassword = new UserNotification(newUser.getEmail(), Events.CREATE, LocalDate.now().toString());
            addressConsumer.complementAddress(new UserCreateAddressDTO(newUser.getCep()));
            userPublisher.sendNotification(notificationUpdatePassword);
        }catch (JsonProcessingException ex){
            throw new RuntimeException("Create missing");
        }
        return UserMapper.toDTO(userRepository.save(UserMapper.toUser(newUser)));
    }
    @Transactional
    public void updateUser(Long id, UserUpdateDTO userUpdate) throws JsonProcessingException {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User id %d not found", id))
        );
        User existing = userRepository.findByEmail(userUpdate.getEmail());
        if (existing != null && !existing.getId().equals(user.getId()))
            throw new UniqueViolationException(
                    String.format("Email with name %s already exists", userUpdate.getEmail())
            );
        UserMapper.updateByDto(userUpdate, user);
        userRepository.save(user);
        UserMapper.toDTO(user);
        UserNotification notificationUpdate = new UserNotification(userUpdate.getEmail(), Events.UPDATE, LocalDate.now().toString());
        userPublisher.sendNotification(notificationUpdate);
        log.info(String.valueOf(notificationUpdate));

    }
    @Transactional
    public void updatePassword(Long id, String oldPassword, PasswordUpdateDTO newPassword) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%s not found", id))
        );
        if (!passwordEncoder.matches(oldPassword, user.getPassword())) {
            throw new PasswordInvalidException("Old password is incorrect");
        }
        if (newPassword.getPassword().equals(oldPassword)) {
            throw new PasswordInvalidException("New password must be different from old password");
        }
        user.setPassword(passwordEncoder.encode(newPassword.getPassword()));
        userRepository.save(user);
        try{
            UserNotification notificationUpdatePassword = new UserNotification(user.getEmail(), Events.UPDATE_PASSWORD, LocalDate.now().toString());
            userPublisher.sendNotification(notificationUpdatePassword);
            log.info(String.valueOf(notificationUpdatePassword));
        }catch (JsonProcessingException ex){
            throw new RuntimeException("Create missing");
        }

    }
    @Transactional(readOnly = true)
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id=%s not detected", id))
        );
        return UserMapper.toDTO(user);
    }
    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        if (!userRepository.existsByEmail(email)){
            throw new EntityNotFoundException("Email not found");
        }else
            return userRepository.findByEmail(email);
    }
}
