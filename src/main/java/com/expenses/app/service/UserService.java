package com.expenses.app.service;

import com.expenses.app.dto.AuthDTO;
import com.expenses.app.model.User;
import com.expenses.app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService implements UserDetailsService {
    
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder; // ✅ Inyectado desde PasswordConfig
    
    @Override
    public UserDetails loadUserByUsername(String correo) throws UsernameNotFoundException {
        User user = userRepository.findByCorreo(correo)
                .orElseThrow(() -> new UsernameNotFoundException("Usuario no encontrado: " + correo));
        
        return org.springframework.security.core.userdetails.User
                .withUsername(user.getCorreo())
                .password(user.getContraseña())
                .authorities("USER")
                .build();
    }
    
    public User registerUser(AuthDTO.RegisterRequest request) {
        if (userRepository.existsByCorreo(request.getCorreo())) {
            throw new RuntimeException("El correo ya está registrado");
        }
        
        User user = new User();
        user.setApodo(request.getApodo());
        user.setCorreo(request.getCorreo());
        user.setContraseña(passwordEncoder.encode(request.getContraseña()));
        
        return userRepository.save(user);
    }
    
    public Optional<User> findByCorreo(String correo) {
        return userRepository.findByCorreo(correo);
    }
    
    public Optional<User> findById(String id) {
        return userRepository.findById(id);
    }
    
    public AuthDTO.UserResponse toUserResponse(User user) {
        return new AuthDTO.UserResponse(user.getId(), user.getApodo(), user.getCorreo());
    }
}