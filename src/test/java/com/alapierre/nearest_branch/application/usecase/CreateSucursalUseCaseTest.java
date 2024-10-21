package com.alapierre.nearest_branch.application.usecase;

import com.alapierre.nearest_branch.application.exception.InvalidSucursalDataException;
import com.alapierre.nearest_branch.domain.model.Sucursal;
import com.alapierre.nearest_branch.domain.repository.SucursalRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CreateSucursalUseCaseTest {

    @Mock
    private SucursalRepository sucursalRepository;

    @InjectMocks
    private CreateSucursalUseCase createSucursalUseCase;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void create_ShouldReturnCreatedSucursal_WhenSucursalIsValid() {
        // Arrange
        Sucursal sucursal = Sucursal.builder().id("1").direccion("Calle 123").latitud(10.0).longitud(20.0).build();
        when(sucursalRepository.save(any(Sucursal.class))).thenReturn(sucursal);

        // Act
        Sucursal result = createSucursalUseCase.create(sucursal);

        // Assert
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("Calle 123", result.getDireccion());
        verify(sucursalRepository, times(1)).save(sucursal);
    }

    @Test
    void create_ShouldThrowInvalidSucursalDataException_WhenDireccionIsEmpty() {
        // Arrange
        Sucursal sucursal = Sucursal.builder().id("1").direccion("").latitud(10.0).longitud(20.0).build();

        // Act & Assert
        InvalidSucursalDataException exception = assertThrows(InvalidSucursalDataException.class, () -> {
            createSucursalUseCase.create(sucursal);
        });

        assertEquals("La dirección de la sucursal no puede estar vacía.", exception.getMessage());
        verify(sucursalRepository, never()).save(any(Sucursal.class)); // Aseguramos que el repositorio no fue llamado
    }
}