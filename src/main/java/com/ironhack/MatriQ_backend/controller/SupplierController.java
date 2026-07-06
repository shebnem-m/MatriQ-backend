package com.ironhack.MatriQ_backend.controller;

import com.ironhack.MatriQ_backend.dto.supplier.SupplierCreateDTO;
import com.ironhack.MatriQ_backend.dto.supplier.SupplierResponseDTO;
import com.ironhack.MatriQ_backend.dto.supplier.SupplierUpdateDTO;
import com.ironhack.MatriQ_backend.service.SupplierService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import lombok.RequiredArgsConstructor;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/suppliers")
@RequiredArgsConstructor
public class SupplierController {

    private final SupplierService supplierService;

    @PostMapping
    public ResponseEntity<SupplierResponseDTO> createSupplier(@Valid @RequestBody SupplierCreateDTO createDTO) {
        SupplierResponseDTO response = supplierService.createSupplier(createDTO);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> getSupplierById(@PathVariable UUID id) {
        SupplierResponseDTO response = supplierService.getSupplierById(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<Page<SupplierResponseDTO>> getAllSuppliers(Pageable pageable) {
        Page<SupplierResponseDTO> response = supplierService.getAllSuppliers(pageable);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<SupplierResponseDTO> updateSupplier(@PathVariable UUID id, @Valid @RequestBody SupplierUpdateDTO updateDTO) {
        SupplierResponseDTO response = supplierService.updateSupplier(id, updateDTO);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteSupplier(@PathVariable UUID id) {
        supplierService.deleteSupplier(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/{id}/listings")
    public ResponseEntity<List<Object>> getSupplierListings(@PathVariable UUID id) {
        // TODO: Integrate with Listing module once Yaqut has finished it.
        // For now, we return an empty list to satisfy the endpoint requirement and compile safely.
        return ResponseEntity.ok(Collections.emptyList());
    }
}
