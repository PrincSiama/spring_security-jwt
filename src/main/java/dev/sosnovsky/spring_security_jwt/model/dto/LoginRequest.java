package dev.sosnovsky.spring_security_jwt.model.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "Адрес электронной почты должен быть заполнен")
    @Email(message = "Необходимо ввести корректный адрес электронной почты")
    private String email;

    @Size(min = 8, message = "Пароль должен содержать не менее 8 символов")
    private String password;
}