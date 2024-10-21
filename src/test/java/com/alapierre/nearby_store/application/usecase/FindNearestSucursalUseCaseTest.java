package com.alapierre.nearby_store.application.usecase;

import com.alapierre.nearby_store.application.exception.SucursalNotFoundException;
import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.domain.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FindNearestSucursalUseCaseTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private FindNearestSucursalUseCase findNearestSucursalUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void findNearest_ShouldReturnNearestSucursal_WhenThereAreSucursales() {
        // Arrange
        Sucursal sucursal1 = Sucursal.builder().id("1").direccion("Sucursal 1").latitud(10.0).longitud(20.0).build();
        Sucursal sucursal2 = Sucursal.builder().id("2").direccion("Sucursal 2").latitud(11.0).longitud(21.0).build();
        Sucursal sucursal3 = Sucursal.builder().id("2").direccion("Sucursal 3").latitud(12.0).longitud(22.0).build();
        Sucursal sucursal4 = Sucursal.builder().id("2").direccion("Sucursal 4").latitud(13.0).longitud(23.0).build();
        Sucursal sucursal5 = Sucursal.builder().id("2").direccion("Sucursal 5").latitud(14.0).longitud(24.0).build();

        List<Sucursal> sucursales = Arrays.asList(sucursal1, sucursal2, sucursal3, sucursal4, sucursal5);

        when(sucursalRepository.findAll()).thenReturn(sucursales);

        // Act
        Sucursal nearest = findNearestSucursalUseCase.findNearest(10.0, 20.0);

        // Assert
        assertNotNull(nearest);
        assertEquals("1", nearest.getId());
        verify(sucursalRepository, times(1)).findAll();
    }

    @Test
    void findNearest_ShouldThrowSucursalNotFoundException_WhenNoSucursalesExist() {
        // Arrange
        when(sucursalRepository.findAll()).thenReturn(Collections.emptyList()); // Simulamos que no hay sucursales

        // Act & Assert
        SucursalNotFoundException exception = assertThrows(SucursalNotFoundException.class, () -> {
            findNearestSucursalUseCase.findNearest(10.0, 20.0);
        });

        assertEquals("No se encontraron sucursales", exception.getMessage());
        verify(sucursalRepository, times(1)).findAll();
    }
}
