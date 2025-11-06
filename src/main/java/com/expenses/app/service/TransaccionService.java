package com.expenses.app.service;

import com.expenses.app.dto.TransaccionDTO;
import com.expenses.app.model.Categoria;
import com.expenses.app.model.TipoTransaccion;
import com.expenses.app.model.Transaccion;
import com.expenses.app.model.User;
import com.expenses.app.repository.TransaccionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class TransaccionService {
    
    private final TransaccionRepository transaccionRepository;
    private final UserService userService;
    private final CategoriaService categoriaService;
    
    public Transaccion createTransaccion(TransaccionDTO.CreateRequest request, String userId) {
        User user = userService.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        
    Categoria categoria = categoriaService.findByIdAndUserId(request.getCategoriaId(), userId);

    Transaccion transaccion = new Transaccion();
    transaccion.setTipoTransaccion(request.getTipoTransaccion());
    transaccion.setCategoriaId(categoria.getId());
    transaccion.setUserId(user.getId());
    transaccion.setDescripcion(request.getDescripcion());
    transaccion.setFecha(request.getFecha() != null ? request.getFecha() : java.time.Instant.now());
    transaccion.setMonto(request.getMonto());
        
        return transaccionRepository.save(transaccion);
    }
    
    @Transactional(readOnly = true)
    public List<TransaccionDTO.Response> getTransaccionesByUser(String userId) {
        return transaccionRepository.findByUserId(userId).stream()
                .map(this::toResponse)
                .collect(Collectors.toList());
    }
    
    public Transaccion updateTransaccion(String id, TransaccionDTO.UpdateRequest request, String userId) {
        Transaccion transaccion = transaccionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        
    Categoria categoria = categoriaService.findByIdAndUserId(request.getCategoriaId(), userId);

    transaccion.setTipoTransaccion(request.getTipoTransaccion());
    transaccion.setCategoriaId(categoria.getId());
    transaccion.setDescripcion(request.getDescripcion());
    transaccion.setFecha(request.getFecha() != null ? request.getFecha() : transaccion.getFecha());
    transaccion.setMonto(request.getMonto());
        
        return transaccionRepository.save(transaccion);
    }
    
    public void deleteTransaccion(String id, String userId) {
        Transaccion transaccion = transaccionRepository.findByIdAndUserId(id, userId)
                .orElseThrow(() -> new RuntimeException("Transacción no encontrada"));
        transaccionRepository.delete(transaccion);
    }
    
    // MÉTODO CORREGIDO - Maneja relaciones lazy correctamente
    public TransaccionDTO.Response toResponse(Transaccion transaccion) {
        // Obtener nombre de categoría de manera segura
        String categoriaNombre = categoriaService.findByIdAndUserId(
            transaccion.getCategoriaId(),
            transaccion.getUserId()
        ).getNombre();

        // Obtener apodo de usuario de manera segura
        String userApodo = userService.findById(transaccion.getUserId())
                .map(User::getApodo)
                .orElse("Usuario no disponible");

        return new TransaccionDTO.Response(
            transaccion.getId(),
            transaccion.getTipoTransaccion(),
            categoriaNombre,
            transaccion.getCategoriaId(),
            transaccion.getDescripcion(),
            transaccion.getFecha(),
            transaccion.getMonto(),
            transaccion.getUserId(),
            userApodo
        );
    }
}