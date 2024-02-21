package io.github.pablitohaddad.msuser.services;

import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.mapper.UserMapper;
import io.github.pablitohaddad.msuser.repositorys.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class UserService {

    private final UserRepository userRepository;

    public UserResponseDTO createUser(UserCreateDTO newUser) {
        String encryptedPassword = new BCryptPasswordEncoder().encode(newUser.getPassword());
        newUser.setPassword(encryptedPassword);
        return UserMapper.toDTO(userRepository.save(UserMapper.toProduct(newUser)));
    }
    public UserResponseDTO login(UserCreateDTO newUser) {
        return UserMapper.toDTO(userRepository.save(UserMapper.toProduct(newUser)));
    }
}
