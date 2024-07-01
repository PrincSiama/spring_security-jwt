package dev.sosnovsky.spring_security_jwt.service;


import dev.sosnovsky.spring_security_jwt.model.User;

import java.security.Principal;

public interface UserPrincipalService {
    User getUserFromPrincipal(Principal principal);
}
