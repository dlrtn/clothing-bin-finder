package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.ClothingBinRepository;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity.ClothingBinEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class ClothingBinEntitySaver {

    private final ClothingBinRepository clothingBinRepository;

    public void save(ClothingBinEntity entity) {
        String point = createPointString(entity.getLatitude(), entity.getLongitude());

        clothingBinRepository.insertClothingBin(
                entity.getManagementCompany(),
                point,
                entity.getDataReferenceDate(),
                entity.getLatitude(),
                entity.getLongitude(),
                entity.getAddress()
        );
    }

    private String createPointString(BigDecimal latitude, BigDecimal longitude) {
        return "POINT(" + latitude + " " + longitude + ")";
    }
}
