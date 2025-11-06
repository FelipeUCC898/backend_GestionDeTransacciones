package com.expenses.app.controller;

import com.expenses.app.dto.CategoriaDTO;
import com.expenses.app.model.Categoria;
import com.expenses.app.service.CategoriaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/categories")
@RequiredArgsConstructor
@Tag(name = "Categorías", description = "Gestión de categorías de transacciones")
public class CategoriaController {

    private final CategoriaService categoriaService;

    @PostMapping
    @Operation(summary = "Crear nueva categoría")
    public ResponseEntity<CategoriaDTO.Response> createCategoria(
            @Valid @RequestBody CategoriaDTO.CreateRequest request,
            @RequestHeader("User-Id") String userId) {
        Categoria categoria = categoriaService.createCategoria(request, userId);
        return ResponseEntity.ok(categoriaService.toResponse(categoria));
    }
                
    @GetMapping("/user/{userId}")
    @Operation(summary = "Obtener categorías de un usuario")
    public ResponseEntity<List<CategoriaDTO.Response>> getCategoriasByUser(@PathVariable String userId) {
        List<CategoriaDTO.Response> categorias = categoriaService.getCategoriasByUser(userId);
        return ResponseEntity.ok(categorias);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Actualizar categoría")
    public ResponseEntity<CategoriaDTO.Response> updateCategoria(
            @PathVariable String id,
            @Valid @RequestBody CategoriaDTO.UpdateRequest request,
            @RequestHeader("User-Id") String userId) {
        Categoria categoria = categoriaService.updateCategoria(id, request, userId);
        return ResponseEntity.ok(categoriaService.toResponse(categoria));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Eliminar categoría")
    public ResponseEntity<Void> deleteCategoria(
            @PathVariable String id,
            @RequestHeader("User-Id") String userId) {
        categoriaService.deleteCategoria(id, userId);
        return ResponseEntity.noContent().build();
    }
}