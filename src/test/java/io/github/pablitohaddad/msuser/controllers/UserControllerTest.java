package io.github.pablitohaddad.msuser.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.pablitohaddad.msuser.dto.UserUpdateDTO;
import io.github.pablitohaddad.msuser.exceptions.UnauthorizedJwtTokenException;
import io.github.pablitohaddad.msuser.exceptions.UpdateFailedException;
import io.github.pablitohaddad.msuser.exceptions.UserNotFoundException;
import io.github.pablitohaddad.msuser.repositorys.UserRepository;
import io.github.pablitohaddad.msuser.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.time.LocalDate;

import static io.github.pablitohaddad.msuser.common.UserConstants.*;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(UserController.class)
class UserControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    WebApplicationContext webApplicationContext;
    @Autowired
    ObjectMapper objectMapper;
    @MockBean
    UserService userService;
    @MockBean
    UserRepository userRepository;

    @BeforeEach
    public void setup(){
       mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();}
    @Test
    void createUser_WithValidData_ReturnsCreated() throws Exception {
        when(userService.createUser(USER_CREATE_DTO)).thenReturn(USER_RESPONSE_DTO);

        mockMvc.perform(post("/v1/users")
                        .content(objectMapper.writeValueAsString(USER_CREATE_DTO))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }
    @Test
    void createUser_WithInvalidData_ReturnsBadRequest() throws Exception {
        when(userService.createUser(INVALID_USER_CREATE_DTO)).thenThrow(RuntimeException.class);
        mockMvc.perform(post("/v1/users")).andExpect(status().isBadRequest());
    }

    @Test
    void getUserById_WithValidId_ReturnsUserResponseDTO() throws Exception {
        when(userService.getUserById(1L)).thenReturn(USER_RESPONSE_DTO);
        mockMvc.perform(
                get("/v1/users/1")
                        .header("Authorization", "Bearer " + VALID_TOKEN.getToken())
                        .contentType(MediaType.APPLICATION_JSON))
                        .andExpect(status().isOk());
    }
    @Test
    void getUserById_WithInvalidId_ReturnsNotFound() throws Exception {
        when(userService.getUserById(anyLong())).thenThrow(UserNotFoundException.class);

        mockMvc.perform(get("/v1/users/0")
                        .header("Authorization", "Bearer " + VALID_TOKEN.getToken()))
                .andExpect(status().isNotFound());
    }
    @Test
    void getUserById_WithInvalidToken_ReturnsUnauthorized() throws Exception {
        when(userService.getUserById(1L)).thenThrow(UnauthorizedJwtTokenException.class);
        mockMvc.perform(get("/v1/users/1")
                        .header("Authorization", "Bearer " + INVALID_TOKEN.getToken()))
                .andExpect(status().isUnauthorized());
    }



    @Test
    void updateUser_WithValidData_ReturnsNewUserResponseDTO() throws Exception{
        UserUpdateDTO dto = new UserUpdateDTO("Joao", "arruda", LocalDate.now(), "emailnovo@email.com", "78211-286");
        when(userService.updateUser(1L, dto)).thenReturn(USER_RESPONSE_DTO);
        mockMvc.perform(put("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_RESPONSE_DTO)))
                .andExpect(status().isOk());
    }
    @Test
    void updateUser_WithInvalidData_ReturnsNewUserResponseDTO() throws Exception{
        UserUpdateDTO dto = new UserUpdateDTO("", "", LocalDate.now(), "", "");
        when(userService.updateUser(1L, dto)).thenThrow(UpdateFailedException.class);
        mockMvc.perform(put("/v1/users/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_RESPONSE_DTO)))
                .andExpect(status().isOk());
    }

    @Test
    void updatePassword() throws Exception {
        when(userService.updatePassword(1L, USER.getPassword(), NEW_PASSWORD)).thenReturn(USER_RESPONSE_DTO);
        mockMvc.perform(put("/v1/users/1/12345678")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(USER_RESPONSE_DTO)))
                .andExpect(status().isNoContent());
    }
}