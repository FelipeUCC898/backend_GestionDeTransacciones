package com.expenses.app.controller;

import com.expenses.app.dto.TransaccionDTO;
import com.expenses.app.model.Transaccion;
import com.expenses.app.service.TransaccionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/transactions")
@RequiredArgsConstructor
@Tag(name = "Transacciones", description = "Gestión de transacciones (ingresos y gastos)")
public class TransaccionController {

    private final TransaccionService transaccionService;

    @PostMapping
    @Operation(summary = "Crear nueva transacción")
    public ResponseEntity<TransaccionDTO.Response> createTransaccion(
            @Valid @RequestBody TransaccionDTO.CreateRequest request,
            @RequestHeader("User-Id") String userId) {
        Transaccion transaccion = transaccionService.createTransaccion(request, userId);
        return ResponseEntity.ok(transaccionService.toResponse(transaccion));
    }

    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener transacciones de un usuario")
    public ResponseEntity<List<TransaccionDTO.Response>> getTransaccionesByUser(@PathVariable String userId) {
        List<TransaccionDTO.Response> transacciones = transaccionService.getTransaccionesByUser(userId);
        return ResponseEntity.ok(transacciones);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar transacción")
    public ResponseEntity<TransaccionDTO.Response> updateTransaccion(
            @PathVariable String id,
            @Valid @RequestBody TransaccionDTO.UpdateRequest request,
            @RequestHeader("User-Id") String userId) {
        Transaccion transaccion = transaccionService.updateTransaccion(id, request, userId);
        return ResponseEntity.ok(transaccionService.toResponse(transaccion));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar transacción")
    public ResponseEntity<Void> deleteTransaccion(
            @PathVariable String id,
            @RequestHeader("User-Id") String userId) {
        transaccionService.deleteTransaccion(id, userId);
        return ResponseEntity.noContent().build();
    }
}