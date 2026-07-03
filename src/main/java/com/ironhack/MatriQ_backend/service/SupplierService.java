package com.ironhack.MatriQ_backend.service;

import com.ironhack.MatriQ_backend.dto.supplier.SupplierCreateDTO;
import com.ironhack.MatriQ_backend.dto.supplier.SupplierResponseDTO;
import com.ironhack.MatriQ_backend.dto.supplier.SupplierUpdateDTO;
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
