package com.ironhack.MatriQ_backend.services.implementation;

import com.ironhack.MatriQ_backend.dtos.ListingCreateDto;
import com.ironhack.MatriQ_backend.dtos.ListingFilterDto;
import com.ironhack.MatriQ_backend.dtos.ListingResponseDto;
import com.ironhack.MatriQ_backend.entities.Listing;
import com.ironhack.MatriQ_backend.mappers.ListingMapper;
import com.ironhack.MatriQ_backend.repositories.ListingRepository;
import com.ironhack.MatriQ_backend.services.ListingService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;

    public ListingServiceImpl(ListingRepository listingRepository, ListingMapper listingMapper) {
        this.listingRepository = listingRepository;
        this.listingMapper = listingMapper;
    }

    @Override
    public ListingResponseDto createListing(ListingCreateDto dto) {
        Listing listing = listingMapper.toEntity(dto);
        Listing savedListing = listingRepository.save(listing);
        return listingMapper.toResponseDto(savedListing);
    }

    @Override
    public Page<ListingResponseDto> getAllListings(Pageable pageable) {
        return listingRepository.findAll(pageable)
                .map(listingMapper::toResponseDto);
    }

    @Override
    public ListingResponseDto getListingById(UUID id) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found with id: " + id));
        return listingMapper.toResponseDto(listing);
    }

    @Override
    public ListingResponseDto updateListing(UUID id, ListingCreateDto dto) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Listing not found with id: " + id));

        listing.setTitle(dto.getTitle());
        listing.setDescription(dto.getDescription());
        listing.setCategory(dto.getCategory());
        listing.setMaterialType(dto.getMaterialType());
        listing.setUnit(dto.getUnit());
        listing.setPrice(dto.getPrice());
        listing.setStockQuantity(dto.getStockQuantity());
        listing.setDeliveryDays(dto.getDeliveryDays());

        Listing updatedListing = listingRepository.save(listing);
        return listingMapper.toResponseDto(updatedListing);
    }

    @Override
    public void deleteListing(UUID id) {
        if (!listingRepository.existsById(id)) {
            throw new RuntimeException("Listing not found with id: " + id);
        }
        listingRepository.deleteById(id);
    }

    @Override
    public Page<ListingResponseDto> searchListings(ListingFilterDto filterDto, Pageable pageable) {
        if (filterDto.getTitle() != null && !filterDto.getTitle().isBlank()) {
            return listingRepository.findByTitleContainingIgnoreCase(filterDto.getTitle(), pageable)
                    .map(listingMapper::toResponseDto);
        }
        if (filterDto.getCategory() != null && !filterDto.getCategory().isBlank()) {
            return listingRepository.findByCategory(filterDto.getCategory(), pageable)
                    .map(listingMapper::toResponseDto);
        }
        if (filterDto.getMinPrice() != null && filterDto.getMaxPrice() != null) {
            return listingRepository.findByPriceBetween(filterDto.getMinPrice(), filterDto.getMaxPrice(), pageable)
                    .map(listingMapper::toResponseDto);
        }

        return getAllListings(pageable);
    }
}