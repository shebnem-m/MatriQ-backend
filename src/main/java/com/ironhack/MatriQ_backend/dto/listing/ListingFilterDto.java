package com.ironhack.MatriQ_backend.dto.listing;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ListingFilterDto {
    private String title;
    private String category;
    private BigDecimal minPrice;
    private BigDecimal maxPrice;
}