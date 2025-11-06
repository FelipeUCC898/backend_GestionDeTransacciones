package com.expenses.app.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.Instant;

@Document(collection = "transacciones")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Transaccion {
    
    @Id
    private String id;
    
    @NotNull
    private TipoTransaccion tipoTransaccion;
    
    // Referencias por ID en MongoDB
    private String categoriaId;
    private String userId;
    
    @NotBlank
    @Size(min = 2, max = 255)
    @Size(min = 2, max = 255)
    private String descripcion;
    
    @NotNull
    private Instant fecha;
    
    @NotNull
    @Positive
    private Double monto;
    
    @CreatedDate
    private Instant createdAt;

    @LastModifiedDate
    private Instant updatedAt;

    // Si fecha no se provee, la lógica de asignación se puede manejar en el servicio antes de persistir
}