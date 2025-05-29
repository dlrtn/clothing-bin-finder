package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity.ClothingBinEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;

@Component
public class CsvDataMapper {

    public ClothingBinEntity mapToEntity(String[] line) {
        if (line == null || line.length < 9) {
            throw new IllegalArgumentException("CSV line does not contain enough data: " + (line != null ? line.length : "null"));
        }

        BigDecimal latitude = null;
        BigDecimal longitude = null;
        LocalDate dataReferenceDate = null;
        String managementCompany = line[7];
        String address = line[4];

        try {
            latitude = BigDecimal.valueOf(Double.parseDouble(line[5]));
            longitude = BigDecimal.valueOf(Double.parseDouble(line[6]));
            dataReferenceDate = LocalDate.parse(line[8]);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Invalid number format in CSV line: " + e.getMessage());
        }

        return ClothingBinEntity.builder()
                .managementCompany(managementCompany)
                .address(address)
                .latitude(latitude)
                .longitude(longitude)
                .dataReferenceDate(dataReferenceDate)
                .build();
    }
}
