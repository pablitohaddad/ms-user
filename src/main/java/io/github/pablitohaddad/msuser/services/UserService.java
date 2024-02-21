package io.github.pablitohaddad.msuser.services;

import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.mapper.UserMapper;
import io.github.pablitohaddad.msuser.entities.User;
import io.github.pablitohaddad.msuser.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserResponseDTO createUser(UserCreateDTO newUser) {
        String encryptedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        return UserMapper.toDTO(userRepository.save(UserMapper.toProduct(newUser)));
    }
    public UserResponseDTO login(UserCreateDTO newUser) {
        return UserMapper.toDTO(userRepository.save(UserMapper.toProduct(newUser)));
    }

    @Transactional(readOnly = true)
    public User findByEmail(String email) {
        return userRepository.findByEmail(email);
    }
}
