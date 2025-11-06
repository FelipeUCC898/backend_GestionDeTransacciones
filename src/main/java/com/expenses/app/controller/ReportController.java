package com.expenses.app.controller;

import com.expenses.app.dto.ReportDTO;
import com.expenses.app.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Reportes financieros y resúmenes")
public class ReportController {
    
    private final ReportService reportService;
    
    @GetMapping("/user/{userId}/summary")
    @Operation(
        summary = "Obtener resumen financiero del usuario",
        description = "Obtiene un resumen de ingresos y gastos del usuario. Si se proporcionan fechas, filtra por el rango especificado."
    )
    @io.swagger.v3.oas.annotations.responses.ApiResponses(value = {
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "200",
            description = "Resumen financiero obtenido exitosamente",
            content = @io.swagger.v3.oas.annotations.media.Content(
                mediaType = "application/json",
                schema = @io.swagger.v3.oas.annotations.media.Schema(implementation = ReportDTO.SummaryResponse.class)
            )
        ),
        @io.swagger.v3.oas.annotations.responses.ApiResponse(
            responseCode = "404",
            description = "Usuario no encontrado"
        )
    })
    @io.swagger.v3.oas.annotations.Parameters({
        @io.swagger.v3.oas.annotations.Parameter(
            name = "userId",
            description = "ID del usuario",
            required = true,
            schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string")
        ),
        @io.swagger.v3.oas.annotations.Parameter(
            name = "fechaInicio",
            description = "Fecha de inicio del rango (formato ISO: yyyy-MM-dd'T'HH:mm:ss'Z')",
            required = false,
            schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", format = "date-time", example = "2025-10-02T00:00:00Z")
        ),
        @io.swagger.v3.oas.annotations.Parameter(
            name = "fechaFin",
            description = "Fecha fin del rango (formato ISO: yyyy-MM-dd'T'HH:mm:ss'Z')",
            required = false,
            schema = @io.swagger.v3.oas.annotations.media.Schema(type = "string", format = "date-time", example = "2025-10-05T23:59:59Z")
        )
    })
    public ResponseEntity<ReportDTO.SummaryResponse> getSummary(
            @PathVariable String userId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaInicio,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime fechaFin) {
        
        Instant fechaInicioInstant = fechaInicio != null ? fechaInicio.toInstant(ZoneOffset.UTC) : null;
        Instant fechaFinInstant = fechaFin != null ? fechaFin.toInstant(ZoneOffset.UTC) : null;
        
        ReportDTO.SummaryResponse summary = reportService.getSummary(userId, fechaInicioInstant, fechaFinInstant);
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/user/{userId}/by-category")
    @Operation(summary = "Obtener reporte de transacciones por categoría")
    public ResponseEntity<ReportDTO.ByCategoryResponse> getByCategory(@PathVariable String userId) {
        ReportDTO.ByCategoryResponse report = reportService.getByCategory(userId);
        return ResponseEntity.ok(report);
    }
}

