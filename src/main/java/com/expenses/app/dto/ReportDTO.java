package com.expenses.app.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.List;
import java.util.Map;

public class ReportDTO {
    
    @Data
    @NoArgsConstructor
    @Schema(description = "Respuesta del resumen financiero")
    public static class SummaryResponse {
        @Schema(description = "Total de ingresos en el período", example = "1500.50")
        private Double totalIngresos;

        @Schema(description = "Total de gastos en el período", example = "750.25")
        private Double totalGastos;

        @Schema(description = "Balance (ingresos - gastos)", example = "750.25")
        private Double balance;

        @Schema(description = "Número total de transacciones", example = "15")
        private Long totalTransacciones;

        @Schema(description = "Fecha de inicio del período", example = "2025-10-02T00:00:00Z")
        private Instant fechaInicio;

        @Schema(description = "Fecha fin del período", example = "2025-10-05T23:59:59Z")
        private Instant fechaFin;
        
        public SummaryResponse(Double totalIngresos, Double totalGastos, Double balance, Long totalTransacciones) {
            this.totalIngresos = totalIngresos;
            this.totalGastos = totalGastos;
            this.balance = balance;
            this.totalTransacciones = totalTransacciones;
        }
        
        public SummaryResponse(Double totalIngresos, Double totalGastos, Double balance, Long totalTransacciones, 
                             Instant fechaInicio, Instant fechaFin) {
            this(totalIngresos, totalGastos, balance, totalTransacciones);
            this.fechaInicio = fechaInicio;
            this.fechaFin = fechaFin;
        }
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class CategorySummary {
        private String categoria;
        private Double totalIngresos;
        private Double totalGastos;
        private Double balance;
    }
    
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ByCategoryResponse {
        private List<CategorySummary> resumenPorCategoria;
        private Map<String, Double> gastosPorCategoria;
        private Map<String, Double> ingresosPorCategoria;
    }
}