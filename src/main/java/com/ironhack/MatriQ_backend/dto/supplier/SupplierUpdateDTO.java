package com.ironhack.MatriQ_backend.dto.supplier;

import  jakarta.validation.constraints.Email;
import lombok.Data;
import jakarta.validation.constraints.NotBlank;

@Data
public class SupplierUpdateDTO {
    
    @NotBlank(message = "Name cannot be blank")
    private String name;
    
    private String description;
    
    @NotBlank(message = "Email cannot be blank")
    @Email(message = "Email should be valid")
    private String email;
    private String phoneNumber;
    private String address;

}
