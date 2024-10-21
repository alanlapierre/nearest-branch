package com.alapierre.nearest_branch.application.usecase;

import com.alapierre.nearest_branch.application.exception.InvalidSucursalDataException;
import com.alapierre.nearest_branch.domain.model.Sucursal;
import com.alapierre.nearest_branch.domain.repository.SucursalRepository;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service
public class CreateSucursalUseCase {

    private final SucursalRepository sucursalRepository;

    public CreateSucursalUseCase(SucursalRepository sucursalRepository) {
        this.sucursalRepository = sucursalRepository;
    }

    public Sucursal create(Sucursal sucursal) {

        if (Objects.isNull(sucursal.getDireccion()) || sucursal.getDireccion().isEmpty()) {
            throw new InvalidSucursalDataException("La dirección de la sucursal no puede estar vacía.");
        }

        return sucursalRepository.save(sucursal);
    }
}