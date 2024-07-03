package dev.sosnovsky.spring.security.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sosnovsky.spring.security.jwt.exception.LoginOrPasswordException;
import dev.sosnovsky.spring.security.jwt.model.Role;
import dev.sosnovsky.spring.security.jwt.model.User;
import dev.sosnovsky.spring.security.jwt.model.dto.LoginRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.TokensResponse;
import dev.sosnovsky.spring.security.jwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class AuthenticationControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @BeforeEach
    void setUp() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest("Пётр", "Петров",
                "petr@petrov.ru", LocalDate.of(2005, 5, 5), "password1");
        User testUser = new ModelMapper().map(registerUserRequest, User.class);
        testUser.setRegistrationDate(LocalDate.now());
        testUser.addRole(Role.USER);
        testUser.setPassword(passwordEncoder.encode(registerUserRequest.getPassword()));
        userRepository.save(testUser);
    }

    @Test
    @DisplayName("Успешное создание токена")
    @DirtiesContext
    void createAuthToken() throws Exception {
        MvcResult mvcResult = mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(
                                new LoginRequest("petr@petrov.ru", "password1")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        TokensResponse response = objectMapper
                .readValue(mvcResult.getResponse().getContentAsString(), TokensResponse.class);
        assertFalse(response.accessToken().isBlank());
        assertFalse(response.refreshToken().isBlank());
    }

    @Test
    @DisplayName("Запрос токенов с неверным паролем")
    @DirtiesContext
    void requestTokensWithInvalidPassword() throws Exception {
        mockMvc.perform(post("/auth/login")
                        .content(objectMapper.writeValueAsString(
                                new LoginRequest("petr@petrov.ru", "password55")))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(result -> assertInstanceOf(LoginOrPasswordException.class,
                        result.getResolvedException()));
    }
}