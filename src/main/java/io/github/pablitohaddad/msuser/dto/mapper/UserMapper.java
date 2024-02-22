package io.github.pablitohaddad.msuser.dto.mapper;

import io.github.pablitohaddad.msuser.dto.PasswordUserDto;
import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.entities.User;
import org.modelmapper.ModelMapper;

public class UserMapper {

    public static UserResponseDTO toDTO(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserResponseDTO.class);
    }

    public static User toProduct(UserCreateDTO createDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(createDto, User.class);
    }

}
