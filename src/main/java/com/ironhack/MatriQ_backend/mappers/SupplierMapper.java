package com.ironhack.MatriQ_backend.mappers;

import com.ironhack.MatriQ_backend.dtos.SupplierCreateDTO;
import com.ironhack.MatriQ_backend.dtos.SupplierResponseDTO;
import com.ironhack.MatriQ_backend.entities.Supplier;
import org.springframework.stereotype.Component;

@Component
public class SupplierMapper {

    public Supplier toEntity(SupplierCreateDTO dto) {
        if (dto == null) {
            return null;
        }
        
        Supplier supplier = new Supplier();
        supplier.setName(dto.getName());
        supplier.setDescription(dto.getDescription());
        supplier.setEmail(dto.getEmail());
        supplier.setPhoneNumber(dto.getPhoneNumber());
        supplier.setAddress(dto.getAddress());
        
        // Note: owner_id will be mapped in the Service layer by fetching the User entity from the DB
        
        return supplier;
    }

    public SupplierResponseDTO toResponseDTO(Supplier entity) {
        if (entity == null) {
            return null;
        }

        SupplierResponseDTO dto = new SupplierResponseDTO();
        dto.setId(entity.getId());
        
        if (entity.getOwner() != null) {
            dto.setOwnerId(entity.getOwner().getId());
        }
        
        dto.setName(entity.getName());
        dto.setDescription(entity.getDescription());
        dto.setEmail(entity.getEmail());
        dto.setPhoneNumber(entity.getPhoneNumber());
        dto.setAddress(entity.getAddress());
        dto.setCreatedAt(entity.getCreatedAt());
        
        return dto;
    }
}
