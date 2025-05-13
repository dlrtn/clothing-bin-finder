package com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.domain.ClothingBin;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "clothing_bin")
public class ClothingBinEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "management_company")
    private String managementCompany;

    // 공간 좌표 직접 매핑은 Hibernate Spatial 필요
    // 여기서는 latitude/longitude만 사용
    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal latitude;

    @Column(nullable = false, precision = 10, scale = 7)
    private BigDecimal longitude;

    @Column(nullable = false)
    private String address;

    @Column(name = "data_reference_date")
    private LocalDate dataReferenceDate;

    @Column(name = "created_at", updatable = false, insertable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", insertable = false)
    private LocalDateTime updatedAt;

    public ClothingBin toDomain() {
        return ClothingBin.builder()
                .id(this.id)
                .managementCompany(this.managementCompany)
                .latitude(Double.valueOf(String.valueOf(this.latitude)))
                .longitude(Double.valueOf(String.valueOf(this.longitude)))
                .address(this.address)
                .dataReferenceDate(this.dataReferenceDate)
                .createdAt(this.createdAt)
                .updatedAt(this.updatedAt)
                .build();
    }
}
