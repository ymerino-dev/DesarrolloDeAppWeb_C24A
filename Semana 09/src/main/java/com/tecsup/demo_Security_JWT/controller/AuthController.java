package com.tecsup.demo_Security_JWT.controller;

import com.tecsup.demo_Security_JWT.dto.*;
import com.tecsup.demo_Security_JWT.entity.Role;
import com.tecsup.demo_Security_JWT.entity.User;
import com.tecsup.demo_Security_JWT.jwt.JwtUtils;
import com.tecsup.demo_Security_JWT.repository.RoleRepository;
import com.tecsup.demo_Security_JWT.repository.UserRepository;
import com.tecsup.demo_Security_JWT.service.UserDetailsImpl;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    public AuthController(AuthenticationManager authenticationManager, UserRepository userRepository,
                          RoleRepository roleRepository, JwtUtils jwtUtils, PasswordEncoder passwordEncoder) {
        this.authenticationManager = authenticationManager; this.userRepository = userRepository;
        this.roleRepository = roleRepository; this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
    }
    @PostMapping("/signup")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse signup(@Valid @RequestBody SignupRequest request) {
        if (userRepository.existsByUsername(request.username()) || userRepository.existsByEmail(request.email()))
            throw new ResponseStatusException(HttpStatus.CONFLICT, "El usuario o correo ya está registrado");
        User user = new User(request.username(), request.email(), passwordEncoder.encode(request.password()));
        Set<String> requested = request.roles() == null || request.roles().isEmpty() ? Set.of("USER") : request.roles();
        for (String raw : requested) {
            String roleName = raw.toUpperCase().replace("ROLE_", "");
            if (!Set.of("USER", "ADMIN").contains(roleName)) throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Rol no permitido: " + raw);
            Role role = roleRepository.findByName(roleName).orElseGet(() -> roleRepository.save(new Role(roleName)));
            user.addRole(role);
        }
        userRepository.save(user);
        return new MessageResponse("Usuario registrado correctamente");
    }
    @PostMapping("/signin")
    public JwtResponse signin(@Valid @RequestBody LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.email(), request.password()));
        UserDetailsImpl principal = (UserDetailsImpl) authentication.getPrincipal();
        List<String> roles = principal.getAuthorities().stream().map(a -> a.getAuthority()).collect(Collectors.toList());
        return new JwtResponse(principal.getId(), principal.getUsername(), principal.getEmail(), roles, jwtUtils.generateJwtToken(authentication));
    }
}
