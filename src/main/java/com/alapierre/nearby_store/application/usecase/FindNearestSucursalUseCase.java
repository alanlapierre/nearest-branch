package com.alapierre.nearby_store.application.usecase;

import com.alapierre.nearby_store.application.exception.SucursalNotFoundException;
import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.domain.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class FindNearestSucursalUseCase {

    private final SucursalRepository sucursalRepository;

    public FindNearestSucursalUseCase(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public Sucursal findNearest(double lat, double lon) {
        List<Sucursal> sucursales = sucursalRepository.findAll();
        return sucursales.stream()
                .min(Comparator.comparingDouble(s -> calculateDistance(lat, lon, s.getLatitud(), s.getLongitud())))
                .orElseThrow(() -> new SucursalNotFoundException("No se encontraron sucursales"));
    }

    private double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double R = 6371; // Radio de la tierra en km
        double dLat = Math.toRadians(lat2 - lat1);
        double dLon = Math.toRadians(lon2 - lon1);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(dLon / 2) * Math.sin(dLon / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        return R * c; // Distancia en km
    }
}
