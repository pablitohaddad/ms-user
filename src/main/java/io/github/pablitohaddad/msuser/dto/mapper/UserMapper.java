package io.github.pablitohaddad.msuser.dto.mapper;

import io.github.pablitohaddad.msuser.dto.UserCreateDTO;
import io.github.pablitohaddad.msuser.dto.UserResponseDTO;
import io.github.pablitohaddad.msuser.dto.UserUpdateDTO;
import io.github.pablitohaddad.msuser.entities.User;
import org.modelmapper.ModelMapper;

public class UserMapper {

    public static UserResponseDTO toDTO(User user){
        ModelMapper mapper = new ModelMapper();
        return mapper.map(user, UserResponseDTO.class);
    }

    public static User toUser(UserCreateDTO createDto) {
        ModelMapper mapper = new ModelMapper();
        return mapper.map(createDto, User.class);
    }

    public static User updateByDto(UserUpdateDTO updateDTO, User user){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setSkipNullEnabled(true); // pula campos nulos
        mapper.map(updateDTO, user);
        return user;
    }
//    public static UserUpdateDTO updateToDTO(User user){
//        ModelMapper mapper = new ModelMapper();
//        return mapper.map(user, UserUpdateDTO.class);
//    }

}
