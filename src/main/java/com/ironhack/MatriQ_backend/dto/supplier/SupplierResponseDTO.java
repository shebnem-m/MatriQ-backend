package com.ironhack.MatriQ_backend.dto.supplier;

import java.time.LocalDateTime;
import lombok.Data;
import java.util.UUID;

@Data
public class SupplierResponseDTO {
    
    private UUID id;
    private UUID ownerId;
    private String  name;
    private String description;
    private String email;
    private String phoneNumber;
    private String address ;
    private LocalDateTime createdAt;

}
