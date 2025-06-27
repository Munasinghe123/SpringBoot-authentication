package com.oAuth.AuthService;

import java.util.Map;

import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@RestController
public class UserController {

    @GetMapping("/user-data")
    public Map<String, Object> getUserInfo(@AuthenticationPrincipal OAuth2User principal) {

        if (principal == null) {
            return Map.of("error", "User not authenticated");
        }

        return Map.of(
                "name", principal.getAttribute("name"),
                "email", principal.getAttribute("email"),
                "picture", principal.getAttribute("picture")

        );
    }
}
