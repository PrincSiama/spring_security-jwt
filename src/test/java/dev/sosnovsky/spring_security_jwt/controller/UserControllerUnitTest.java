package dev.sosnovsky.spring_security_jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sosnovsky.spring_security_jwt.config.SecurityConfig;
import dev.sosnovsky.spring_security_jwt.jwt.JwtRequestFilter;
import dev.sosnovsky.spring_security_jwt.jwt.JwtTokenUtils;
import dev.sosnovsky.spring_security_jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.UserDto;
import dev.sosnovsky.spring_security_jwt.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = UserController.class)
@ContextConfiguration(classes = SecurityConfig.class)
class UserControllerUnitTest {

    @MockBean
    private UserService userService;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private JwtTokenUtils jwtTokenUtils;
    @MockBean
    private JwtRequestFilter jwtRequestFilter;
    @Autowired
    private ObjectMapper objectMapper;
    @MockBean
    private UserDetailsService userDetailsService;

    @Test
    @DisplayName("Корректное создание пользователя")
    void createUserTest() throws Exception {
        RegisterUserRequest request = new RegisterUserRequest("Пётр", "Петров",
                "petr@petrov.ru", LocalDate.of(2005, 5, 5), "password1");

        when(userService.create(any(RegisterUserRequest.class))).thenReturn(new UserDto());

        mockMvc.perform(post("/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Корректное получение пользователя по id")
    void getUserByIdTest() throws Exception {
        when(userService.getById(anyInt())).thenReturn(new UserDto());

        mockMvc.perform(get("/users/1"))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Корректное получение пользователей")
    void getAllUsersTest() throws Exception {
        when(userService.getAllUsers(any(Pageable.class))).thenReturn(List.of(new UserDto(), new UserDto()));

        mockMvc.perform(get("/users"))
                .andExpect(status().isOk());
    }
}