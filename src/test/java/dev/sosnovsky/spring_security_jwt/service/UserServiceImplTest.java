package dev.sosnovsky.spring_security_jwt.service;

import dev.sosnovsky.spring_security_jwt.exception.NotFoundException;
import dev.sosnovsky.spring_security_jwt.model.Role;
import dev.sosnovsky.spring_security_jwt.model.User;
import dev.sosnovsky.spring_security_jwt.model.dto.RegisterUserRequest;
import dev.sosnovsky.spring_security_jwt.model.dto.UserDto;
import dev.sosnovsky.spring_security_jwt.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.modelmapper.ModelMapper;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    private UserService userService;
    @Mock
    private UserPrincipalService userPrincipalService;
    private final ModelMapper modelMapper = new ModelMapper();
    private final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    @Mock
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        userService = new UserServiceImpl(userRepository, modelMapper, passwordEncoder);
    }

    @Test
    @DisplayName("Корректное создание пользователя")
    @DirtiesContext
    public void createUserTest() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest("Пётр", "Петров",
                "petr@petrov.ru", LocalDate.of(2005, 5, 5), "password1");

        when(userRepository.save(Mockito.any(User.class))).then(returnsFirstArg());

        UserDto user = userService.create(registerUserRequest);

        verify(userRepository).save(Mockito.any(User.class));
        assertNotNull(user);
        assertEquals(LocalDate.now(), user.getRegistrationDate());
        assertTrue(user.getRoles().contains(Role.USER));
        assertEquals(registerUserRequest.getEmail(), user.getEmail());
    }

    @Test
    @DisplayName("Корректное получение пользователя по id")
    public void getUserByIdTest() {
        int customUserId = 47;

        RegisterUserRequest registerUserRequest = new RegisterUserRequest("Иван", "Иванов", "ivan@ivanov.ru",
                LocalDate.of(2000, 10, 1), "password1");
        User testUser = modelMapper.map(registerUserRequest, User.class);
        testUser.setId(customUserId);

        when(userRepository.findById(anyInt())).thenReturn(Optional.of(testUser));

        UserDto userDto = userService.getById(customUserId);

        assertNotNull(userDto);
        assertEquals(registerUserRequest.getFirstname(), userDto.getFirstname());
        assertEquals(registerUserRequest.getLastname(), userDto.getLastname());
        assertEquals(registerUserRequest.getEmail(), userDto.getEmail());
    }

    @Test
    @DisplayName("Получение несуществующего пользователя")
    public void getUserByIdWithNotExistUserTest() {
        int customUserId = 49;

        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(NotFoundException.class, () -> userService.getById(customUserId));
    }
}