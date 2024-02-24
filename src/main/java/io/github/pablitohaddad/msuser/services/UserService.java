package io.github.pablitohaddad.msuser.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import io.github.pablitohaddad.msuser.dto.PasswordUpdateDTO;
import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.UserUpdateDTO;
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
    @Transactional
    public UserResponseDTO createUser(UserCreateDTO newUser) {
            String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(encryptedPassword);
            if(userRepository.existsByEmail(newUser.getEmail())){
                throw new UniqueViolationException("Email already exists");
            }if(userRepository.existsByCpf(newUser.getCpf())){
                throw new UniqueViolationException("Cpf already exists");
            }else{
                try{
                    userPublisher.sendNotification(new UserNotification(newUser.getEmail(), Events.CREATE, LocalDate.now().toString()));
                }catch (JsonProcessingException ex){
                    throw new RuntimeException("Create missing");
                }
                return UserMapper.toDTO(userRepository.save(UserMapper.toUser(newUser)));
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
    @Transactional
    public void updateUser(Long id, UserUpdateDTO userUpdate) {
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
    }
}
