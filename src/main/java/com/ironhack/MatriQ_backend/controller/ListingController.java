package com.ironhack.MatriQ_backend.controller;

import com.ironhack.MatriQ_backend.dto.listing.ListingCreateDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingFilterDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingResponseDto;
import com.ironhack.MatriQ_backend.service.ListingService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@RestController
@RequestMapping("/listings")
@RequiredArgsConstructor
public class ListingController {

    private final ListingService listingService;

    // YENİ: Fayl yükləməni dəstəkləyən create metodu
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ListingResponseDto> createListing(
            @Valid @RequestPart("listing") ListingCreateDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(listingService.createListing(dto, file));
    }

    // YENİ: Fayl yükləməni dəstəkləyən update metodu
    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ListingResponseDto> updateListing(
            @PathVariable UUID id,
            @Valid @RequestPart("listing") ListingCreateDto dto,
            @RequestPart(value = "file", required = false) MultipartFile file
    ) {
        return ResponseEntity.ok(listingService.updateListing(id, dto, file));
    }

    // Digər metodların (GET, DELETE) olduğu kimi qalır...
    @GetMapping
    public ResponseEntity<Page<ListingResponseDto>> getAllListings(
            @RequestParam(defaultValue = "price-low") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {
        if(size > 50) size = 50;
        return ResponseEntity.ok(listingService.getAllListings(createPageable(sort, page, size)));
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ListingResponseDto>> searchListings(
            @ModelAttribute ListingFilterDto filterDto,
            @RequestParam(defaultValue = "price-low") String sort,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "12") int size
    ) {

        System.out.println("TITLE = " + filterDto.getTitle());

        Pageable pageable = createPageable(sort, page, size);

        return ResponseEntity.ok(listingService.searchListings(filterDto, pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDto> getListingById(@PathVariable UUID id){
        return ResponseEntity.ok(listingService.getListingById(id));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteListing(@PathVariable UUID id){
        listingService.deleteListing(id);
        return ResponseEntity.noContent().build();
    }

    private Pageable createPageable(String sort, int page, int size){
        Sort sorting;
        switch(sort){
            case "price-high": sorting = Sort.by(Sort.Direction.DESC, "price"); break;
            case "newest": sorting = Sort.by(Sort.Direction.DESC, "createdAt"); break;
            default: sorting = Sort.by(Sort.Direction.ASC, "price");
        }
        return PageRequest.of(page, size, sorting);
    }
}