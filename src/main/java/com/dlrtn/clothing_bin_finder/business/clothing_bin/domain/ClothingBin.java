package com.dlrtn.clothing_bin_finder.business.clothing_bin.domain;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity.ClothingBinEntity;
import lombok.Builder;
import lombok.Getter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Builder
public class ClothingBin {

    private Integer id;

    private String managementCompany;

    private Double latitude;

    private Double longitude;

    private String address;

    private LocalDate dataReferenceDate;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    public ClothingBinEntity toEntity() {
        return ClothingBinEntity.builder()
                .id(this.id)
                .managementCompany(this.managementCompany)
                .latitude(BigDecimal.valueOf(this.latitude))
                .longitude(BigDecimal.valueOf(this.longitude))
                .address(this.address)
                .dataReferenceDate(this.dataReferenceDate)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
