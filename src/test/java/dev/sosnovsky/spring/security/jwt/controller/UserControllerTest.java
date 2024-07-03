package dev.sosnovsky.spring.security.jwt.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.sosnovsky.spring.security.jwt.exception.NotFoundException;
import dev.sosnovsky.spring.security.jwt.jwt.JwtTokenUtils;
import dev.sosnovsky.spring.security.jwt.model.Role;
import dev.sosnovsky.spring.security.jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring.security.jwt.model.dto.UserDto;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class UserControllerTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    private JwtTokenUtils jwtTokenUtils;
    @Autowired
    private ObjectMapper objectMapper;
    @Mock
    private UserDetails userDetails;

    @Test
    @DisplayName("Запрос списка пользователей пользователем с ролью ADMIN")
    void getUsersWithRoleAdmin() throws Exception {
        when(userDetails.getUsername()).thenReturn("Admin");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Role.ADMIN.name());
        when(userDetails.getAuthorities()).thenAnswer(invocationOnMock -> List.of(grantedAuthority));
        String token = jwtTokenUtils.generateAccessToken(userDetails);
        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());
    }

    @Test
    @DisplayName("Запрос списка пользователей пользователем с ролью USER")
    void getUsersWithRoleUser() throws Exception {
        when(userDetails.getUsername()).thenReturn("User");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Role.USER.name());
        when(userDetails.getAuthorities()).thenAnswer(invocationOnMock -> List.of(grantedAuthority));
        String token = jwtTokenUtils.generateAccessToken(userDetails);

        mockMvc.perform(get("/users")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }

    @Test
    @DisplayName("Назначение роли ADMIN пользователем с ролью ADMIN")
    @DirtiesContext
    void setAdminRoleWithRoleAdmin() throws Exception {
        when(userDetails.getUsername()).thenReturn("Admin");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Role.ADMIN.name());
        when(userDetails.getAuthorities()).thenAnswer(invocationOnMock -> List.of(grantedAuthority));
        String token = jwtTokenUtils.generateAccessToken(userDetails);

        mockMvc.perform(post("/users/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isNotFound())
                .andExpect(result -> assertInstanceOf(NotFoundException.class, result.getResolvedException()));

        RegisterUserRequest registerUserRequest = new RegisterUserRequest("Пётр", "Петров",
                "petr@petrov.ru", LocalDate.of(2005, 5, 5), "password1");

        MvcResult mvcResult = mockMvc.perform(post("/users")
                        .content(objectMapper.writeValueAsString(registerUserRequest))
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDto userDto = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), UserDto.class);

        assertNotNull(userDto);
        assertEquals(registerUserRequest.getEmail(), userDto.getEmail());
        assertTrue(userDto.getRoles().contains(Role.USER));

        mockMvc.perform(post("/users/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isOk());

        MvcResult mvcResultAdmin = mockMvc.perform(get("/users/" + userDto.getId())
                        .header("Authorization", "Bearer " + token)
                        .characterEncoding(StandardCharsets.UTF_8)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        UserDto userDtoAdmin = objectMapper.readValue(mvcResultAdmin.getResponse().getContentAsString(), UserDto.class);

        assertNotNull(userDtoAdmin);
        assertEquals(registerUserRequest.getEmail(), userDtoAdmin.getEmail());
        assertTrue(userDtoAdmin.getRoles().contains(Role.ADMIN));
    }

    @Test
    @DisplayName("Назначение роли ADMIN пользователем с ролью USER")
    void setAdminRoleWithRoleUser() throws Exception {
        when(userDetails.getUsername()).thenReturn("User");
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(Role.USER.name());
        when(userDetails.getAuthorities()).thenAnswer(invocationOnMock -> List.of(grantedAuthority));
        String token = jwtTokenUtils.generateAccessToken(userDetails);

        mockMvc.perform(post("/users/1")
                        .header("Authorization", "Bearer " + token))
                .andExpect(status().isForbidden());
    }
}