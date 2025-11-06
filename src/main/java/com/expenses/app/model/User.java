package com.expenses.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "users")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    
    @Id
    private String id;
    
    @NotBlank
    @Size(min = 2, max = 50)
    private String apodo;
    
    @NotBlank
    @Email
    @Indexed(unique = true)
    private String correo;
    
    @NotBlank
    @Size(min = 6)
    private String contraseña;
    
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;
    
    // En MongoDB, podemos tener referencias o documentos embebidos
    // Para simplicidad, usaremos referencias por ID
    private List<String> categoriaIds = new ArrayList<>();
    private List<String> transaccionIds = new ArrayList<>();
    
    // Auditoría manejada por Spring Data MongoDB (@CreatedDate / @LastModifiedDate)
}
