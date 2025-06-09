package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.ClothingBinRepository;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity.ClothingBinEntity;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class ClothingBinEntitySaver {

    private final ClothingBinRepository clothingBinRepository;

    @Transactional
    public void saveAll(Iterable<ClothingBinEntity> entities) {
        for (ClothingBinEntity entity : entities) {
            save(entity);
        }
    }

    private void save(ClothingBinEntity entity) {
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
        return String.format("POINT(%s %s)", latitude, longitude);
    }
}
