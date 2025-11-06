package com.expenses.app.repository;

import com.expenses.app.model.Categoria;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CategoriaRepository extends MongoRepository<Categoria, String> {
    List<Categoria> findByUserId(String userId);
    Optional<Categoria> findByIdAndUserId(String id, String userId);
    Boolean existsByNombreAndUserId(String nombre, String userId);
}