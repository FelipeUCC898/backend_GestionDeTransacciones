package com.expenses.app.dto;

import com.expenses.app.model.TipoTransaccion;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.Instant;

public class TransaccionDTO {
    
    public static class CreateRequest {
        @NotNull
        private TipoTransaccion tipoTransaccion;
        
        @NotNull
            private String categoriaId;
        
        @NotBlank
        @Size(min = 2, max = 255)
        private String descripcion;
        
    private Instant fecha;
        
        @NotNull
        @Positive
        private Double monto;
        
        // Getters y Setters
        public TipoTransaccion getTipoTransaccion() { return tipoTransaccion; }
        public void setTipoTransaccion(TipoTransaccion tipoTransaccion) { this.tipoTransaccion = tipoTransaccion; }
            public String getCategoriaId() { return categoriaId; }
            public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }
        public Double getMonto() { return monto; }
        public void setMonto(Double monto) { this.monto = monto; }
    }
    
    public static class UpdateRequest {
        @NotNull
        private TipoTransaccion tipoTransaccion;
        
        @NotNull
            private String categoriaId;
        
        @NotBlank
        @Size(min = 2, max = 255)
        private String descripcion;
        
    private Instant fecha;
        
        @NotNull
        @Positive
        private Double monto;
        
        // Getters y Setters
        public TipoTransaccion getTipoTransaccion() { return tipoTransaccion; }
        public void setTipoTransaccion(TipoTransaccion tipoTransaccion) { this.tipoTransaccion = tipoTransaccion; }
            public String getCategoriaId() { return categoriaId; }
            public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }
        public Double getMonto() { return monto; }
        public void setMonto(Double monto) { this.monto = monto; }
    }
    
    public static class Response {
            private String id;
        private TipoTransaccion tipoTransaccion;
        private String categoriaNombre;
            private String categoriaId;
        private String descripcion;
    private Instant fecha;
        private Double monto;
            private String userId;
        private String userApodo;
        
    public Response() {}
        
        public Response(String id, TipoTransaccion tipoTransaccion, String categoriaNombre, 
                   String categoriaId, String descripcion, Instant fecha, 
                   Double monto, String userId, String userApodo) {
            this.id = id;
            this.tipoTransaccion = tipoTransaccion;
            this.categoriaNombre = categoriaNombre;
            this.categoriaId = categoriaId;
            this.descripcion = descripcion;
            this.fecha = fecha;
            this.monto = monto;
            this.userId = userId;
            this.userApodo = userApodo;
        }
        
        // Getters y Setters
            public String getId() { return id; }
            public void setId(String id) { this.id = id; }
        public TipoTransaccion getTipoTransaccion() { return tipoTransaccion; }
        public void setTipoTransaccion(TipoTransaccion tipoTransaccion) { this.tipoTransaccion = tipoTransaccion; }
        public String getCategoriaNombre() { return categoriaNombre; }
        public void setCategoriaNombre(String categoriaNombre) { this.categoriaNombre = categoriaNombre; }
            public String getCategoriaId() { return categoriaId; }
            public void setCategoriaId(String categoriaId) { this.categoriaId = categoriaId; }
        public String getDescripcion() { return descripcion; }
        public void setDescripcion(String descripcion) { this.descripcion = descripcion; }
    public Instant getFecha() { return fecha; }
    public void setFecha(Instant fecha) { this.fecha = fecha; }
        public Double getMonto() { return monto; }
        public void setMonto(Double monto) { this.monto = monto; }
            public String getUserId() { return userId; }
            public void setUserId(String userId) { this.userId = userId; }
        public String getUserApodo() { return userApodo; }
        public void setUserApodo(String userApodo) { this.userApodo = userApodo; }
    }
}