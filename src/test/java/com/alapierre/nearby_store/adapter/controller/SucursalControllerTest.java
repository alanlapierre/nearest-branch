package com.alapierre.nearby_store.adapter.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;

import com.alapierre.nearby_store.adapter.mapper.SucursalMapper;
import com.alapierre.nearby_store.application.exception.InvalidSucursalDataException;
import com.alapierre.nearby_store.application.exception.SucursalNotFoundException;
import com.alapierre.nearby_store.application.service.SucursalService;
import com.alapierre.nearby_store.application.usecase.CreateSucursalUseCase;
import com.alapierre.nearby_store.application.usecase.FindNearestSucursalUseCase;
import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.infrastructure.dto.SucursalDTO;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

@WebMvcTest(SucursalController.class)
class SucursalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private SucursalService sucursalService;

    @MockBean
    private CreateSucursalUseCase createSucursalUseCase;

    @MockBean
    private FindNearestSucursalUseCase findNearestSucursalUseCase;

    private ObjectMapper objectMapper = new ObjectMapper();


    //******************** BUSCAR SUCURSAL POR ID *******************

    @Test
    void getById_ShouldReturnValidSucursal_WhenDataIsValid() throws Exception {
        // Arrange
        Sucursal sucursal = Sucursal.builder().id("1").direccion("Calle 123").latitud(10.0).longitud(20.0).build();
        when(sucursalService.findById(anyString())).thenReturn(sucursal);

        // Act & Assert
        mockMvc.perform(get("/api/sucursales/1")
                    .accept("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sucursal.getId()))
                .andExpect(jsonPath("$.direccion").value(sucursal.getDireccion()));
    }

    @Test
    void getById_ShouldReturnStatusNotFound404_WhenDataIsInvalid() throws Exception {
        doThrow(new SucursalNotFoundException("Sucursal no encontrada")).when(sucursalService).findById(anyString());

        mockMvc.perform(get("/api/sucursales/1")
                        .accept("application/json"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Sucursal no encontrada"));
    }

    //******************** CREACION DE SUCURSAL *******************


    @Test
    void create_ShouldReturnCreatedSucursal_WhenDataIsValid() throws Exception {
        // Arrange
        Sucursal sucursal = Sucursal.builder().id("1").direccion("Calle 123").latitud(10.0).longitud(20.0).build();
        SucursalDTO sucursalDTO = SucursalMapper.toDTO(sucursal);
        when(createSucursalUseCase.create(any(Sucursal.class))).thenReturn(sucursal);

        // Act & Assert
        mockMvc.perform(post("/api/sucursales")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sucursalDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sucursal.getId()))
                .andExpect(jsonPath("$.direccion").value(sucursal.getDireccion()));
    }

    @Test
    void create_ShouldReturnStatusBadRequest400_WhenDireccionIsEmpty() throws Exception {
        doThrow(new InvalidSucursalDataException("Datos inválidos")).when(createSucursalUseCase).create(any());

        mockMvc.perform(post("/api/sucursales")
                        .contentType("application/json")
                        .content("{\"direccion\":\"\", \"latitud\":10.0, \"longitud\":20.0}"))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message").value("Datos inválidos"));
    }


    //******************** BUSCAR SUCURSAL CERCANA *******************


    @Test
    void getNearest_ShouldReturnValidSucursal_WhenDataIsValid() throws Exception {
        // Arrange
        Sucursal sucursal = Sucursal.builder().id("1").direccion("Calle 123").latitud(10.0).longitud(20.0).build();
        when(findNearestSucursalUseCase.findNearest(anyDouble(), anyDouble())).thenReturn(sucursal);

        // Act & Assert
        mockMvc.perform(get("/api/sucursales/cercana")
                        .param("lat", "10.0")
                        .param("lon", "20.0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(sucursal.getId()))
                .andExpect(jsonPath("$.direccion").value(sucursal.getDireccion()));
    }

    @Test
    void getNearest_ShouldReturnStatusNotFound404_WhenSucursalNotFound() throws Exception {
        doThrow(new SucursalNotFoundException("No se encontraron sucursales")).when(findNearestSucursalUseCase).findNearest(anyDouble(), anyDouble());

        mockMvc.perform(get("/api/sucursales/cercana")
                        .param("lat", "10.0")
                        .param("lon", "20.0"))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("No se encontraron sucursales"));
    }


    //******************** SIMILAMOS UN INTERNAL SERVER ERROR - STATUS 500 *******************

    @Test
    void create_ShouldReturnStatusInternalServerError500_WhenUnexpectedExceptionThrown() throws Exception {
        // Arrange
        SucursalDTO sucursalDTO = SucursalDTO.builder().id("1").direccion("Calle 123").latitud(10.0).longitud(20.0).build();

        // Simulamos que ocurre una RuntimeException inesperada cuando se llama al caso de uso
        doThrow(new RuntimeException("Unexpected error")).when(createSucursalUseCase).create(any(Sucursal.class));

        // Act & Assert
        mockMvc.perform(post("/api/sucursales")
                        .contentType("application/json")
                        .content(objectMapper.writeValueAsString(sucursalDTO)))
                .andExpect(status().isInternalServerError())  // HTTP 500
                .andExpect(jsonPath("$.message").value("Ocurrió un error interno, por favor inténtalo más tarde."))
                .andExpect(jsonPath("$.timestamp").exists());  // Verificamos que el campo timestamp existe
    }
}