package com.ironhack.MatriQ_backend.service.implementation;

import com.ironhack.MatriQ_backend.dto.listing.ListingCreateDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingFilterDto;
import com.ironhack.MatriQ_backend.dto.listing.ListingResponseDto;
import com.ironhack.MatriQ_backend.entity.Listing;
import com.ironhack.MatriQ_backend.exception.ResourceNotFoundException;
import com.ironhack.MatriQ_backend.mapper.ListingMapper;
import com.ironhack.MatriQ_backend.repository.ListingRepository;
import com.ironhack.MatriQ_backend.service.ListingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ListingServiceImpl implements ListingService {

    private final ListingRepository listingRepository;
    private final ListingMapper listingMapper;

    private final String UPLOAD_DIR = "uploads/";

    @Override
    public ListingResponseDto createListing(ListingCreateDto dto, MultipartFile file) {
        Listing listing = listingMapper.toEntity(dto);

        if (file != null && !file.isEmpty()) {
            listing.setImageUrl(saveFile(file));
        }

        Listing savedListing = listingRepository.save(listing);
        return listingMapper.toResponseDto(savedListing);
    }

    @Override
    public ListingResponseDto updateListing(UUID id, ListingCreateDto dto, MultipartFile file) {
        Listing listing = listingRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with id: " + id));

        listing.setTitle(dto.getTitle());
        listing.setDescription(dto.getDescription());
        listing.setCategory(dto.getCategory());
        listing.setMaterialType(dto.getMaterialType());
        listing.setUnit(dto.getUnit());
        listing.setPrice(dto.getPrice());
        listing.setStockQuantity(dto.getStockQuantity());
        listing.setDeliveryDays(dto.getDeliveryDays());

        if (file != null && !file.isEmpty()) {
            listing.setImageUrl(saveFile(file));
        }

        Listing updatedListing = listingRepository.save(listing);
        return listingMapper.toResponseDto(updatedListing);
    }

    private String saveFile(MultipartFile file) {
        try {
            Files.createDirectories(Paths.get(UPLOAD_DIR));
            String fileName = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
            Path path = Paths.get(UPLOAD_DIR + fileName);
            Files.copy(file.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
            return "/uploads/" + fileName;
        } catch (IOException e) {
            throw new RuntimeException("Could not save file: " + e.getMessage());
        }
    }

    @Override
    public Page<ListingResponseDto> getAllListings(Pageable pageable) {
        return listingRepository.findAll(pageable).map(listingMapper::toResponseDto);
    }

    @Override
    public ListingResponseDto getListingById(UUID id) {
        return listingRepository.findById(id)
                .map(listingMapper::toResponseDto)
                .orElseThrow(() -> new ResourceNotFoundException("Listing not found with id: " + id));
    }

    @Override
    public void deleteListing(UUID id) {
        if (!listingRepository.existsById(id)) throw new ResourceNotFoundException("Listing not found with id: " + id);
        listingRepository.deleteById(id);
    }

    @Override
    public Page<ListingResponseDto> searchListings(ListingFilterDto filterDto, Pageable pageable) {
        return getAllListings(pageable);
    }
}