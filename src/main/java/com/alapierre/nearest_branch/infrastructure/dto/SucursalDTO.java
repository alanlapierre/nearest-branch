package com.alapierre.nearest_branch.infrastructure.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class SucursalDTO {
    private String id;
    private String direccion;
    private double latitud;
    private double longitud;
}
