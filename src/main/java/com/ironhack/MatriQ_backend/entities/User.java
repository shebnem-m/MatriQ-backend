package com.ironhack.MatriQ_backend.entities;

import jakarta.persistence.*;
import java.util.UUID;

@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    // Placeholder entity. Shabnam will implement the rest of the fields (fullName, email, password, role, etc.).
    // This is just to satisfy the Supplier entity's foreign key relationship for now.
    
    public User() {}

    public User(UUID id) {
        this.id = id;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }
}
