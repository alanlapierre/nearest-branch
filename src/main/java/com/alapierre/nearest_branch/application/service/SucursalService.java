package com.alapierre.nearest_branch.application.service;

import com.alapierre.nearest_branch.application.exception.SucursalNotFoundException;
import com.alapierre.nearest_branch.domain.model.Sucursal;
import com.alapierre.nearest_branch.domain.repository.SucursalRepository;
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