package com.expenses.app.service;

import com.expenses.app.dto.CategoriaDTO;
import com.expenses.app.model.Categoria;
import com.expenses.app.model.User;
import com.expenses.app.repository.CategoriaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class CategoriaService {
    
    private final CategoriaRepository categoriaRepository;
    private final UserService userService;
    
    public Categoria createCategoria(CategoriaDTO.CreateRequest request, String userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
        if (categoriaRepository.existsByNombreAndUserId(request.getNombre(), userId)) {
            throw new RuntimeException("Ya existe una categoría con este nombre");
        }
        
    Categoria categoria = new Categoria();
    categoria.setNombre(request.getNombre());
    categoria.setUserId(user.getId());
        
        return categoriaRepository.save(categoria);
    }
    
    @Transactional(readOnly = true)
    public List<CategoriaDTO.Response> getCategoriasByUser(String userId) {
        return categoriaRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public Categoria updateCategoria(String id, CategoriaDTO.UpdateRequest request, String userId) {
        Categoria categoria = categoriaRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        
        if (!categoria.getNombre().equals(request.getNombre()) && 
            categoriaRepository.existsByNombreAndUserId(request.getNombre(), userId)) {
            throw new RuntimeException("Ya existe una categoría con este nombre");
        }
        
        categoria.setNombre(request.getNombre());
        return categoriaRepository.save(categoria);
    }
    
    public void deleteCategoria(String id, String userId) {
        Categoria categoria = categoriaRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
        categoriaRepository.delete(categoria);
    }
    
    // MÉTODO CORREGIDO - Maneja la relación lazy correctamente
    public CategoriaDTO.Response toResponse(Categoria categoria) {
    // En lugar de acceder a categoria.getUser(), usamos userId y consultamos el servicio
    String userApodo = userService.findById(categoria.getUserId())
        .map(User::getApodo)
        .orElse("Usuario no disponible");

    return new CategoriaDTO.Response(
        categoria.getId(),
        categoria.getNombre(),
        categoria.getUserId(),
        userApodo
    );
    }
    
    public Categoria findByIdAndUserId(String id, String userId) {
        return categoriaRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Categoría no encontrada"));
    }
}