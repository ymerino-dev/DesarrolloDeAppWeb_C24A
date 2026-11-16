package com.tecsup.demo_Security_JWT.dto;

import java.util.List;

public record JwtResponse(Long id, String username, String email, List<String> roles, String accessToken, String tokenType) {
    public JwtResponse(Long id, String username, String email, List<String> roles, String accessToken) {
        this(id, username, email, roles, accessToken, "Bearer");
    }
}
