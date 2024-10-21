package com.alapierre.nearest_branch.domain.repository;

import com.alapierre.nearest_branch.domain.model.Sucursal;

import java.util.List;
import java.util.Optional;

public interface SucursalRepository {
    Sucursal save(Sucursal sucursal);
    Optional<Sucursal> findById(String id);
    List<Sucursal> findAll();
}
