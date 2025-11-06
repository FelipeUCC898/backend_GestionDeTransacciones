package com.expenses.app.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CategoriaDTO {
    
    public static class CreateRequest {
        @NotBlank
        @Size(min = 2, max = 100)
        private String nombre;
        
        // Getters y Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }
    
    public static class UpdateRequest {
        @NotBlank
        @Size(min = 2, max = 100)
        private String nombre;
        
        // Getters y Setters
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
    }
    
    public static class Response {
           private String id;
        private String nombre;
           private String userId;
        private String userApodo;
        
        public Response() {}
        
           public Response(String id, String nombre, String userId, String userApodo) {
            this.id = id;
            this.nombre = nombre;
            this.userId = userId;
            this.userApodo = userApodo;
        }
        
        // Getters y Setters
           public String getId() { return id; }
           public void setId(String id) { this.id = id; }
        public String getNombre() { return nombre; }
        public void setNombre(String nombre) { this.nombre = nombre; }
           public String getUserId() { return userId; }
           public void setUserId(String userId) { this.userId = userId; }
        public String getUserApodo() { return userApodo; }
        public void setUserApodo(String userApodo) { this.userApodo = userApodo; }
    }
}