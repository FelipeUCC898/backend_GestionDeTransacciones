package com.expenses.app.controller;

import com.expenses.app.dto.ReportDTO;
import com.expenses.app.service.ReportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/reports")
@RequiredArgsConstructor
@Tag(name = "Reportes", description = "Reportes financieros y resúmenes")
public class ReportController {
    
    private final ReportService reportService;
    
    @GetMapping("/user/{userId}/summary")
    @Operation(summary = "Obtener resumen financiero del usuario")
    public ResponseEntity<ReportDTO.SummaryResponse> getSummary(@PathVariable String userId) {
        ReportDTO.SummaryResponse summary = reportService.getSummary(userId);
        return ResponseEntity.ok(summary);
    }
    
    @GetMapping("/user/{userId}/by-category")
    @Operation(summary = "Obtener reporte de transacciones por categoría")
    public ResponseEntity<ReportDTO.ByCategoryResponse> getByCategory(@PathVariable String userId) {
        ReportDTO.ByCategoryResponse report = reportService.getByCategory(userId);
        return ResponseEntity.ok(report);
    }
}

