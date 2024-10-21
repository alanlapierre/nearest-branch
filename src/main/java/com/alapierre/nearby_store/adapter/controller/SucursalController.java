package com.alapierre.nearby_store.adapter.controller;

import com.alapierre.nearby_store.adapter.mapper.SucursalMapper;
import com.alapierre.nearby_store.application.service.SucursalService;
import com.alapierre.nearby_store.application.usecase.CreateSucursalUseCase;
import com.alapierre.nearby_store.application.usecase.FindNearestSucursalUseCase;
import com.alapierre.nearby_store.domain.model.Sucursal;
import com.alapierre.nearby_store.infrastructure.dto.SucursalDTO;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;



@RestController
@RequestMapping("/api/sucursales")
public class SucursalController {

    private final CreateSucursalUseCase createSucursalUseCase;
    private final FindNearestSucursalUseCase findNearestSucursalUseCase;

    private final SucursalService sucursalService;

    public SucursalController(CreateSucursalUseCase createSucursalUseCase, FindNearestSucursalUseCase findNearestSucursalUseCase, SucursalService sucursalService) {
        this.createSucursalUseCase = createSucursalUseCase;
        this.findNearestSucursalUseCase = findNearestSucursalUseCase;
        this.sucursalService = sucursalService;
    }

    @PostMapping
    public ResponseEntity<SucursalDTO> create(@RequestBody SucursalDTO sucursalDTO) {
        Sucursal sucursal = SucursalMapper.toEntity(sucursalDTO);
        Sucursal created = createSucursalUseCase.create(sucursal);
        return ResponseEntity.ok(SucursalMapper.toDTO(created));
    }

    @GetMapping("/{id}")
    public ResponseEntity<SucursalDTO> getById(@PathVariable String id) {
        Sucursal sucursal = sucursalService.findById(id);
        return ResponseEntity.ok(SucursalMapper.toDTO(sucursal));
    }

    @GetMapping("/cercana")
    public ResponseEntity<SucursalDTO> getNearest(@RequestParam double lat, @RequestParam double lon) {
        Sucursal nearest = findNearestSucursalUseCase.findNearest(lat, lon);
        return ResponseEntity.ok(SucursalMapper.toDTO(nearest));
    }
}