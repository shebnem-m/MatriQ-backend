package com.ironhack.MatriQ_backend.service;

import com.ironhack.MatriQ_backend.dto.listing.ListingCreateDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingFilterDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingResponseDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;


public interface ListingService {


    ListingResponseDto createListing(
            ListingCreateDto dto
    );


    Page<ListingResponseDto> getAllListings(
            Pageable pageable
    );


    ListingResponseDto getListingById(
            UUID id
    );


    ListingResponseDto updateListing(
            UUID id,
            ListingCreateDto dto
    );


    void deleteListing(
            UUID id
    );


    Page<ListingResponseDto> searchListings(
            ListingFilterDto filterDto,
            Pageable pageable
    );

}