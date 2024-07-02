package dev.sosnovsky.spring_security_jwt.model.dto;

import dev.sosnovsky.spring_security_jwt.model.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private int id;

    private String firstname;

    private String lastname;

    private String email;

    private LocalDate birthday;

    private Set<Role> roles;

    private LocalDate registrationDate;
}
