package com.alapierre.nearby_store.application.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.alapierre.nearby_store.application.exception.SucursalNotFoundException;
import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.domain.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

class SucursalServiceTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private SucursalService sucursalService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findById_ShouldReturnSucursal_WhenSucursalExists() {
        // Arrange
        Sucursal sucursal = Sucursal.builder().id("1").direccion("Calle 123").latitud(10.0).longitud(20.0).build();
        when(sucursalRepository.findById("1")).thenReturn(Optional.of(sucursal));

        // Act
        Sucursal result = sucursalService.findById("1");

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Calle 123", result.getDireccion());
        verify(sucursalRepository, times(1)).findById("1");
    }

    @Test
    void findById_ShouldThrowSucursalNotFoundException_WhenSucursalDoesNotExist() {
        // Arrange
        when(sucursalRepository.findById("1")).thenReturn(Optional.empty());

        // Act & Assert
        SucursalNotFoundException exception = assertThrows(SucursalNotFoundException.class, () -> {
            sucursalService.findById("1");
        });

        assertEquals("Sucursal no encontrada", exception.getMessage());
        verify(sucursalRepository, times(1)).findById("1");
    }
}
