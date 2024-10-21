package com.alapierre.nearby_store.application.usecase;

import com.alapierre.nearby_store.application.exception.InvalidSucursalDataException;
import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.domain.repository.SucursalRepository;
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