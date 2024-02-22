package io.github.pablitohaddad.msuser.services;

import io.github.pablitohaddad.msuser.dto.PasswordUserDto;
import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.mapper.UserMapper;
import io.github.pablitohaddad.msuser.entities.User;
import io.github.pablitohaddad.msuser.exceptions.PasswordInvalidException;
import io.github.pablitohaddad.msuser.exceptions.UniqueViolationException;
import io.github.pablitohaddad.msuser.repositorys.UserRepository;
import jakarta.persistence.EntityExistsException;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Transactional
    public UserResponseDTO createUser(UserCreateDTO newUser) {
            String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
            newUser.setPassword(encryptedPassword);
            if(userRepository.existsByEmail(newUser.getEmail())){
                throw new UniqueViolationException("Email already exists");
            }if(userRepository.existsByCpf(newUser.getCpf())){
                throw new UniqueViolationException("Cpf already exists");
            }else return UserMapper.toDTO(userRepository.save(UserMapper.toProduct(newUser)));
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
    public void updatePassword(Long id, String oldPassword, PasswordUserDto newPassword) {
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
