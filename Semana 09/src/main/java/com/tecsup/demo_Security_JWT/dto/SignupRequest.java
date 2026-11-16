package com.tecsup.demo_Security_JWT.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import java.util.Set;

public record SignupRequest(@NotBlank @Size(max = 50) String username, @Email @NotBlank String email,
                            @NotBlank @Size(min = 6, max = 100) String password, Set<String> roles) {}
