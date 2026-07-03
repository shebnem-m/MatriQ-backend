package com.ironhack.MatriQ_backend.dto.supplier;

import jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.util.UUID;

@Data
public class SupplierCreateDTO {
    
    @NotNull(message  = "Owner ID cannot be null")
    private UUID ownerId;
    
    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    private  String description;
    
    @NotBlank(message =  "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    
    private String phoneNumber;
    private String address;

}
