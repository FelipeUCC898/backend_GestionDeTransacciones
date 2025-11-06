package com.expenses.app.repository;

import com.expenses.app.model.TipoTransaccion;
import com.expenses.app.model.Transaccion;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TransaccionRepository extends MongoRepository<Transaccion, String> {
    List<Transaccion> findByUserId(String userId);
    List<Transaccion> findByUserIdAndTipoTransaccion(String userId, TipoTransaccion tipoTransaccion);
    Optional<Transaccion> findByIdAndUserId(String id, String userId);
    
    // Consultas agregadas para reportes
    @Query(value = "{ 'userId': ?0, 'tipoTransaccion': ?1 }", fields = "{ 'monto': 1 }")
    List<Transaccion> findMontosByUserIdAndTipoTransaccion(String userId, TipoTransaccion tipoTransaccion);
}