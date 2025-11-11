package com.expenses.app.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import java.util.Arrays;

/**
 * Configuración CORS global que se ejecuta ANTES de Spring Security
 * para asegurar que las peticiones OPTIONS (preflight) sean procesadas correctamente
 */
@Configuration
public class CorsConfig {

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        
        // Orígenes específicos permitidos
        config.setAllowedOrigins(Arrays.asList(
            "https://frontendgestiondetransacciones.vercel.app",
            "http://localhost:5173",
            "http://localhost:3000",
            "http://localhost:5174" // Por si Vite usa otro puerto
        ));
        
        // Métodos HTTP permitidos
        config.setAllowedMethods(Arrays.asList("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH"));
        
        // Headers permitidos (todos)
        config.setAllowedHeaders(Arrays.asList("*"));
        
        // Headers expuestos al cliente
        config.setExposedHeaders(Arrays.asList("Authorization", "Content-Type", "X-Total-Count"));
        
        // Permitir credenciales (cookies, headers de autenticación)
        config.setAllowCredentials(true);
        
        // Tiempo de caché del preflight (1 hora)
        config.setMaxAge(3600L);
        
        // Aplicar configuración a todas las rutas
        source.registerCorsConfiguration("/**", config);
        
        return new CorsFilter(source);
    }
}

