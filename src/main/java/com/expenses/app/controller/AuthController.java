package com.expenses.app.controller;

import com.expenses.app.dto.AuthDTO;
import com.expenses.app.model.User;
import com.expenses.app.service.JwtService;
import com.expenses.app.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/users")
@RequiredArgsConstructor


@Tag(name = "Autenticación", description = "Endpoints para registro y login de usuarios")
public class AuthController {

    private final UserService userService;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    @PostMapping("/register")
    @Operation(summary = "Registrar nuevo usuario")
    public ResponseEntity<AuthDTO.AuthResponse> register(@Valid @RequestBody AuthDTO.RegisterRequest request) {
        User user = userService.registerUser(request);

        String jwtToken = jwtService.generateToken(userService.loadUserByUsername(user.getCorreo()));
        AuthDTO.UserResponse userResponse = userService.toUserResponse(user);

        return ResponseEntity.ok(new AuthDTO.AuthResponse(jwtToken, userResponse));
    }
    @PostMapping("/login")
    @Operation(summary = "Iniciar sesión")
    public ResponseEntity<AuthDTO.AuthResponse> login(@Valid @RequestBody AuthDTO.LoginRequest request) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getCorreo(), request.getContraseña()));

        User user = userService.findByCorreo(request.getCorreo())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        String jwtToken = jwtService.generateToken((UserDetails) authentication.getPrincipal());
        AuthDTO.UserResponse userResponse = userService.toUserResponse(user);

        return ResponseEntity.ok(new AuthDTO.AuthResponse(jwtToken, userResponse));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Obtener información del usuario")
    public ResponseEntity<AuthDTO.UserResponse> getUser(@PathVariable String id) {
        User user = userService.findById(id)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        return ResponseEntity.ok(userService.toUserResponse(user));
    }
}