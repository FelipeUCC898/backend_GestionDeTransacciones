package com.expenses.app.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class AuthDTO {
    
    public static class LoginRequest {
        @NotBlank
        @Email
        private String correo;
        
        @NotBlank
        @Size(min = 6)
        private String contraseña;
        
        // Getters y Setters
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContraseña() { return contraseña; }
        public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    }
    
    public static class RegisterRequest {
        @NotBlank
        @Size(min = 2, max = 50)
        private String apodo;
        
        @NotBlank
        @Email
        private String correo;
        
        @NotBlank
        @Size(min = 6)
        private String contraseña;
        
        // Getters y Setters
        public String getApodo() { return apodo; }
        public void setApodo(String apodo) { this.apodo = apodo; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
        public String getContraseña() { return contraseña; }
        public void setContraseña(String contraseña) { this.contraseña = contraseña; }
    }
    
    public static class AuthResponse {
        private String token;
        private String type = "Bearer";
        private UserResponse user;
        
        public AuthResponse() {}
        
        public AuthResponse(String token, UserResponse user) {
            this.token = token;
            this.user = user;
        }
        
        // Getters y Setters
        public String getToken() { return token; }
        public void setToken(String token) { this.token = token; }
        public String getType() { return type; }
        public void setType(String type) { this.type = type; }
        public UserResponse getUser() { return user; }
        public void setUser(UserResponse user) { this.user = user; }
    }
    
    public static class UserResponse {
           private String id;
        private String apodo;
        private String correo;
        
        public UserResponse() {}
        
           public UserResponse(String id, String apodo, String correo) {
            this.id = id;
            this.apodo = apodo;
            this.correo = correo;
        }
        
        // Getters y Setters
           public String getId() { return id; }
           public void setId(String id) { this.id = id; }
        public String getApodo() { return apodo; }
        public void setApodo(String apodo) { this.apodo = apodo; }
        public String getCorreo() { return correo; }
        public void setCorreo(String correo) { this.correo = correo; }
    }
}