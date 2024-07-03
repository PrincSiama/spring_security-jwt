package dev.sosnovsky.spring.security.jwt.repository;

import dev.sosnovsky.spring.security.jwt.model.Role;
import dev.sosnovsky.spring.security.jwt.model.User;
import dev.sosnovsky.spring.security.jwt.model.dto.RegisterUserRequest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.annotation.DirtiesContext;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class UserRepositoryTest {

    @Autowired
    private UserRepository userRepository;

    private User user;

    @BeforeEach
    void setUp() {
        RegisterUserRequest registerUserRequest = new RegisterUserRequest("Пётр", "Петров",
                "petr@petrov.ru", LocalDate.of(2005, 5, 5), "password1");
        User testUser = new ModelMapper().map(registerUserRequest, User.class);
        testUser.setRegistrationDate(LocalDate.now());
        testUser.addRole(Role.USER);
        testUser.setPassword(registerUserRequest.getPassword());
        user = userRepository.save(testUser);
    }

    @Test
    @DirtiesContext
    @DisplayName("Получение пользователя по email")
    void findByEmail() {
        String fakeEmail = "ivan@divan.com";
        Optional<User> userOptional1 = userRepository.findByEmail(fakeEmail);
        assertTrue(userOptional1.isEmpty());

        Optional<User> userOptional2 = userRepository.findByEmail(user.getEmail());
        assertTrue(userOptional2.isPresent());

        User testUser = userOptional2.get();
        assertNotNull(testUser);
        assertEquals(user.getId(), testUser.getId());
        assertEquals(user.getFirstname(), testUser.getFirstname());
    }

    @Test
    @DirtiesContext
    @DisplayName("Получение пользователя по id")
    void findById() {
        int fakeId = 999;
        Optional<User> userOptional1 = userRepository.findById(fakeId);
        assertTrue(userOptional1.isEmpty());

        Optional<User> userOptional2 = userRepository.findById(user.getId());
        assertTrue(userOptional2.isPresent());

        User testUser = userOptional2.get();
        assertNotNull(testUser);
        assertEquals(user.getEmail(), testUser.getEmail());
        assertEquals(user.getFirstname(), testUser.getFirstname());
    }
}