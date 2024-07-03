package dev.sosnovsky.spring.security.jwt.model.dto;

import dev.sosnovsky.spring.security.jwt.model.Role;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "dto пользователя")
public class UserDto {

    private int id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDate birthday;

    private Set<Role> roles;

    private LocalDate registrationDate;
}
