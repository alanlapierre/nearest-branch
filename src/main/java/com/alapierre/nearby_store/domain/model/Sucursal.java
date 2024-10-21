package com.alapierre.nearby_store.domain.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Sucursal {
    private String id;
    private String direccion;
    private double latitud;
    private double longitud;
}
