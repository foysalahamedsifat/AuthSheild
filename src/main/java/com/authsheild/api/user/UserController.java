package com.authsheild.api.user;

import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {

    @GetMapping("/me")
    public Object me(Authentication auth) {
        return new Object() {
            public final String email = auth.getName();
            public final Object roles = auth.getAuthorities();
        };
    }
}
