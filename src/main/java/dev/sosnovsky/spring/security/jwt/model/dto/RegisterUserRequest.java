package dev.sosnovsky.spring.security.jwt.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "Регистрация пользователя")
public class RegisterUserRequest {

    @NotBlank(message = "Поле не может быть пустым")
    private String firstname;

    @NotBlank(message = "Поле не может быть пустым")
    private String lastname;

    @NotBlank(message = "Адрес электронной почты должен быть заполнен")
    @Email(message = "Необходимо ввести корректный адрес электронной почты")
    private String email;

    @PastOrPresent(message = "Дата должна быть в прошлом")
    private LocalDate birthday;

    @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
    private String password;
}