package com.alapierre.nearby_store.application.service;

import com.alapierre.nearby_store.application.exception.SucursalNotFoundException;
import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.domain.repository.SucursalRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SucursalService {

    @Autowired
    private SucursalRepository sucursalRepository;


    public Sucursal findById(String id) {
        return sucursalRepository.findById(id)
                .orElseThrow(() -> new SucursalNotFoundException("Sucursal no encontrada"));
    }


}