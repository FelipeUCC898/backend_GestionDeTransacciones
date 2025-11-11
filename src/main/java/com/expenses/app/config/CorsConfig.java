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
        
        // Permitir CUALQUIER origen (para proyecto público/demo)
        // Usa setAllowedOriginPatterns en lugar de setAllowedOrigins
        // para permitir wildcard (*) con allowCredentials(true)
        config.setAllowedOriginPatterns(Arrays.asList("*"));
        
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

