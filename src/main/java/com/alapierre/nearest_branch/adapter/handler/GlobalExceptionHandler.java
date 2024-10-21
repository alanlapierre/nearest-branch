package com.alapierre.nearest_branch.adapter.handler;

import com.alapierre.nearest_branch.application.exception.InvalidSucursalDataException;
import com.alapierre.nearest_branch.application.exception.SucursalNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    // Manejo de Sucursal no encontrada
    @ExceptionHandler(SucursalNotFoundException.class)
    public ResponseEntity<Object> handleSucursalNotFoundException(SucursalNotFoundException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND); // HTTP 404
    }

    // Manejo de datos inválidos de sucursal
    @ExceptionHandler(InvalidSucursalDataException.class)
    public ResponseEntity<Object> handleInvalidSucursalDataException(InvalidSucursalDataException ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        return new ResponseEntity<>(body, HttpStatus.BAD_REQUEST); // HTTP 400
    }

    // Manejo genérico de cualquier otra excepción no controlada
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleGlobalException(Exception ex, WebRequest request) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Ocurrió un error interno, por favor inténtalo más tarde.");
        return new ResponseEntity<>(body, HttpStatus.INTERNAL_SERVER_ERROR); // HTTP 500
    }
}
