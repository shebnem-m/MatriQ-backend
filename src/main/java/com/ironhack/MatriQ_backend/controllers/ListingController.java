package com.ironhack.MatriQ_backend.controllers;

import com.ironhack.MatriQ_backend.dto.listing.ListingCreateDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingFilterDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingResponseDto;
import com.ironhack.MatriQ_backend.services.ListingService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/listings")
public class ListingController {

    private final ListingService listingService;


    public ListingController(ListingService listingService) {
        this.listingService = listingService;
    }

    @PostMapping
    public ResponseEntity<ListingResponseDto> createListing(@Valid @RequestBody ListingCreateDto dto) {
        return new ResponseEntity<>(listingService.createListing(dto), HttpStatus.CREATED);
    }


    @GetMapping
    public ResponseEntity<Page<ListingResponseDto>> getAllListings(Pageable pageable) {
        return ResponseEntity.ok(listingService.getAllListings(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDto> getListingById(@PathVariable UUID id) {
        return ResponseEntity.ok(listingService.getListingById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ListingResponseDto> updateListing(@PathVariable UUID id, @Valid @RequestBody ListingCreateDto dto) {
        return ResponseEntity.ok(listingService.updateListing(id, dto));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteListing(@PathVariable UUID id) {
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ListingResponseDto>> searchListings(@ModelAttribute ListingFilterDto filterDto, Pageable pageable) {
        return ResponseEntity.ok(listingService.searchListings(filterDto, pageable));
    }
}