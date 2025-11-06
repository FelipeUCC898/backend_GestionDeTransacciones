package com.expenses.app.service;

import com.expenses.app.dto.ReportDTO;
import com.expenses.app.model.TipoTransaccion;
import com.expenses.app.model.Transaccion;
import com.expenses.app.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import java.time.Instant;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ReportService {
    
    private final TransaccionRepository transaccionRepository;
    private final CategoriaService categoriaService;
    
    public ReportDTO.SummaryResponse getSummary(String userId, Instant fechaInicio, Instant fechaFin) {
        List<Transaccion> transacciones;
        
        if (fechaInicio != null && fechaFin != null) {
            transacciones = transaccionRepository.findByUserIdAndFechaBetween(userId, fechaInicio, fechaFin);
        } else {
            transacciones = transaccionRepository.findByUserId(userId);
        }
        
        Double totalIngresos = transacciones.stream()
                .filter(t -> t.getTipoTransaccion() == TipoTransaccion.INGRESO)
                .mapToDouble(Transaccion::getMonto)
                .sum();
        
        Double totalGastos = transacciones.stream()
                .filter(t -> t.getTipoTransaccion() == TipoTransaccion.GASTO)
                .mapToDouble(Transaccion::getMonto)
                .sum();
        
        return new ReportDTO.SummaryResponse(
            totalIngresos,
            totalGastos,
            totalIngresos - totalGastos,
            (long) transacciones.size(),
            fechaInicio,
            fechaFin
        );
    }
    
    // Método sobrecargado para mantener compatibilidad con el anterior
    public ReportDTO.SummaryResponse getSummary(String userId) {
        return getSummary(userId, null, null);
    }
    
    public ReportDTO.ByCategoryResponse getByCategory(String userId) {
        List<Transaccion> transacciones = transaccionRepository.findByUserId(userId);
        
        // Agrupar por categoría
        Map<String, ReportDTO.CategorySummary> summaryMap = new HashMap<>();
        
        for (Transaccion transaccion : transacciones) {
            String categoriaNombre = categoriaService.findByIdAndUserId(transaccion.getCategoriaId(), userId)
                    .getNombre();
            
            ReportDTO.CategorySummary summary = summaryMap.getOrDefault(categoriaNombre, 
                new ReportDTO.CategorySummary(categoriaNombre, 0.0, 0.0, 0.0));
            
            if (transaccion.getTipoTransaccion() == TipoTransaccion.INGRESO) {
                summary.setTotalIngresos(summary.getTotalIngresos() + transaccion.getMonto());
            } else {
                summary.setTotalGastos(summary.getTotalGastos() + transaccion.getMonto());
            }
            
            summary.setBalance(summary.getTotalIngresos() - summary.getTotalGastos());
            summaryMap.put(categoriaNombre, summary);
        }
        
        List<ReportDTO.CategorySummary> resumenPorCategoria = summaryMap.values().stream()
                .collect(Collectors.toList());
        
        Map<String, Double> gastosPorCategoria = summaryMap.entrySet().stream()
                .filter(entry -> entry.getValue().getTotalGastos() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getTotalGastos()));
        
        Map<String, Double> ingresosPorCategoria = summaryMap.entrySet().stream()
                .filter(entry -> entry.getValue().getTotalIngresos() > 0)
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> entry.getValue().getTotalIngresos()));
        
        return new ReportDTO.ByCategoryResponse(resumenPorCategoria, gastosPorCategoria, ingresosPorCategoria);
    }
}