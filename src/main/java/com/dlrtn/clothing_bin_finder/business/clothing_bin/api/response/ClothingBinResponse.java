package com.dlrtn.clothing_bin_finder.business.clothing_bin.api.response;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.domain.ClothingBin;
import lombok.Builder;

import java.time.LocalDate;

@Builder
public record ClothingBinResponse(
        Integer id,
        String managementCompany,
        Double latitude,
        Double longitude,
        String address,
        LocalDate dataReferenceDate
) {
    public static ClothingBinResponse fromDomain(ClothingBin clothingBin) {
        return ClothingBinResponse.builder()
                .id(clothingBin.getId())
                .managementCompany(clothingBin.getManagementCompany())
                .latitude(clothingBin.getLatitude())
                .longitude(clothingBin.getLongitude())
                .address(clothingBin.getAddress())
                .dataReferenceDate(clothingBin.getDataReferenceDate())
                .build();
    }
}
