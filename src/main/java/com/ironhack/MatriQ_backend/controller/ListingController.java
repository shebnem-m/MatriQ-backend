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
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;


@RestController
@RequestMapping("/listings")
@RequiredArgsConstructor
public class ListingController {


    private final ListingService listingService;



    @PostMapping
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ListingResponseDto> createListing(
            @Valid @RequestBody ListingCreateDto dto
    ) {

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(listingService.createListing(dto));
    }





    @GetMapping
    public ResponseEntity<Page<ListingResponseDto>> getAllListings(

            @RequestParam(
                    defaultValue = "price-low"
            )
            String sort,


            @RequestParam(
                    defaultValue = "0"
            )
            int page,


            @RequestParam(
                    defaultValue = "12"
            )
            int size

    ) {


        if(size > 50){
            size = 50;
        }


        Pageable pageable = createPageable(
                sort,
                page,
                size
        );


        return ResponseEntity.ok(
                listingService.getAllListings(pageable)
        );
    }





    private Pageable createPageable(
            String sort,
            int page,
            int size
    ){


        Sort sorting;


        switch(sort){


            case "price-low":

                sorting = Sort.by(
                        Sort.Direction.ASC,
                        "price"
                );

                break;



            case "price-high":

                sorting = Sort.by(
                        Sort.Direction.DESC,
                        "price"
                );

                break;



            case "newest":

                sorting = Sort.by(
                        Sort.Direction.DESC,
                        "createdAt"
                );

                break;



            default:

                sorting = Sort.by(
                        Sort.Direction.ASC,
                        "price"
                );

        }


        return PageRequest.of(
                page,
                size,
                sorting
        );

    }







    @GetMapping("/search")
    public ResponseEntity<Page<ListingResponseDto>> searchListings(
            @ModelAttribute ListingFilterDto filterDto,
            Pageable pageable
    ){

        return ResponseEntity.ok(
                listingService.searchListings(
                        filterDto,
                        pageable
                )
        );

    }







    @GetMapping("/{id}")
    public ResponseEntity<ListingResponseDto> getListingById(
            @PathVariable UUID id
    ){

        return ResponseEntity.ok(
                listingService.getListingById(id)
        );

    }







    @PutMapping("/{id}")
    @PreAuthorize("hasAnyRole('ADMIN', 'MANAGER')")
    public ResponseEntity<ListingResponseDto> updateListing(
            @PathVariable UUID id,
            @Valid @RequestBody ListingCreateDto dto
    ){

        return ResponseEntity.ok(
                listingService.updateListing(id, dto)
        );

    }







    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteListing(
            @PathVariable UUID id
    ){

        listingService.deleteListing(id);

        return ResponseEntity.noContent().build();

    }

}