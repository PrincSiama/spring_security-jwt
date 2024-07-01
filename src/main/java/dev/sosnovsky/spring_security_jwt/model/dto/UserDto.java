package dev.sosnovsky.spring_security_jwt.model.dto;

import dev.sosnovsky.spring_security_jwt.model.Role;
import lombok.Data;

import java.time.LocalDate;
import java.util.Set;

@Data
public class UserDto {

    private String firstname;

    private String lastname;

    private String email;

    private LocalDate birthday;

    private Set<Role> roles;

    private LocalDate registrationDate;
}
