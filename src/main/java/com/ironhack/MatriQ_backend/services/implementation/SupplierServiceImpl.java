package com.ironhack.MatriQ_backend.services.impl;

import com.ironhack.MatriQ_backend.dto.supplier.SupplierCreateDTO;
import com.ironhack.MatriQ_backend.dto.supplier.SupplierResponseDTO;
import com.ironhack.MatriQ_backend.dto.supplier.SupplierUpdateDTO;
import com.ironhack.MatriQ_backend.entities.Supplier;
import com.ironhack.MatriQ_backend.entities.User;
import com.ironhack.MatriQ_backend.exceptions.ResourceNotFoundException;
import com.ironhack.MatriQ_backend.mappers.SupplierMapper;
import com.ironhack.MatriQ_backend.repositories.SupplierRepository;
import com.ironhack.MatriQ_backend.services.SupplierService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SupplierServiceImpl implements SupplierService {

    private final SupplierRepository supplierRepository;
    private final SupplierMapper supplierMapper;

    public SupplierServiceImpl(SupplierRepository supplierRepository, SupplierMapper supplierMapper) {
        this.supplierRepository = supplierRepository;
        this.supplierMapper = supplierMapper;
    }

    @Override
    public SupplierResponseDTO createSupplier(SupplierCreateDTO createDTO) {
        Supplier supplier = supplierMapper.toEntity(createDTO);
        
        // Use our placeholder User entity to set the foreign key
        supplier.setOwner(new User(createDTO.getOwnerId()));
        
        Supplier savedSupplier = supplierRepository.save(supplier);
        return supplierMapper.toResponseDTO(savedSupplier);
    }

    @Override
    public SupplierResponseDTO getSupplierById(UUID id) {
        Supplier supplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));
        return supplierMapper.toResponseDTO(supplier);
    }

    @Override
    public Page<SupplierResponseDTO> getAllSuppliers(Pageable pageable) {
        Page<Supplier> suppliers = supplierRepository.findAll(pageable);
        return suppliers.map(supplierMapper::toResponseDTO);
    }

    @Override
    public SupplierResponseDTO updateSupplier(UUID id, SupplierUpdateDTO updateDTO) {
        Supplier existingSupplier = supplierRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Supplier not found with id: " + id));

        existingSupplier.setName(updateDTO.getName());
        existingSupplier.setDescription(updateDTO.getDescription());
        existingSupplier.setEmail(updateDTO.getEmail());
        existingSupplier.setPhoneNumber(updateDTO.getPhoneNumber());
        existingSupplier.setAddress(updateDTO.getAddress());

        Supplier savedSupplier = supplierRepository.save(existingSupplier);
        return supplierMapper.toResponseDTO(savedSupplier);
    }

    @Override
    public void deleteSupplier(UUID id) {
        if (!supplierRepository.existsById(id)) {
            throw new ResourceNotFoundException("Supplier not found with id: " + id);
        }
        supplierRepository.deleteById(id);
    }
}
