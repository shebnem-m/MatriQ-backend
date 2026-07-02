package com.ironhack.MatriQ_backend.services;

import com.ironhack.MatriQ_backend.dtos.SupplierCreateDTO;
import com.ironhack.MatriQ_backend.dtos.SupplierResponseDTO;
import com.ironhack.MatriQ_backend.dtos.SupplierUpdateDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface SupplierService {
    SupplierResponseDTO createSupplier(SupplierCreateDTO createDTO);
    SupplierResponseDTO getSupplierById(UUID id);
    Page<SupplierResponseDTO> getAllSuppliers(Pageable pageable);
    SupplierResponseDTO updateSupplier(UUID id, SupplierUpdateDTO updateDTO);
    void deleteSupplier(UUID id);
}
