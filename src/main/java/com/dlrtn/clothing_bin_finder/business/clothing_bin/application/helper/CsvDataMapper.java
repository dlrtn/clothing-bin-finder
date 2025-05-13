package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity.ClothingBinEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CsvDataMapper {

    public ClothingBinEntity mapToEntity(String[] line) {
        String managementCompany = line[7];
        String address = line[4];
        BigDecimal latitude = BigDecimal.valueOf(Double.parseDouble(line[5]));
        BigDecimal longitude = BigDecimal.valueOf(Double.parseDouble(line[6]));
        LocalDate dataReferenceDate = LocalDate.parse(line[8]);

        return ClothingBinEntity.builder()
                .managementCompany(managementCompany)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .dataReferenceDate(dataReferenceDate)
                .build();
    }
}
