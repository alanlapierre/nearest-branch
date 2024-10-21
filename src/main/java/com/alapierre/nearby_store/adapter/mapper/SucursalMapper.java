package com.alapierre.nearby_store.adapter.mapper;

import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.infrastructure.dto.SucursalDTO;

public class SucursalMapper {

    public static Sucursal toEntity(SucursalDTO dto) {
        return Sucursal.builder()
                .id(dto.getId())
                .direccion(dto.getDireccion())
                .latitud(dto.getLatitud())
                .longitud(dto.getLongitud())
                .build();
    }

    public static SucursalDTO toDTO(Sucursal sucursal) {
        return SucursalDTO.builder()
                .id(sucursal.getId())
                .direccion(sucursal.getDireccion())
                .latitud(sucursal.getLatitud())
                .longitud(sucursal.getLongitud())
                .build();
    }
}
