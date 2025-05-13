package com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity.ClothingBinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

public interface ClothingBinRepository extends JpaRepository<ClothingBinEntity, Long> {
    @Query(nativeQuery = true, value = """
            SELECT * FROM clothing_bin
            WHERE ST_Distance_Sphere(
                point(longitude, latitude),
                point(:longitude, :latitude)
            ) <= :distance
            """)
    List<ClothingBinEntity> findAllByLocation(Double latitude, Double longitude, Integer distance);

    @Modifying
    @Query(nativeQuery = true, value = """
            INSERT INTO clothing_bin (
                management_company,
                location,
                data_reference_date,
                latitude,
                longitude,
                address
            ) VALUES (
                :managementCompany,
                ST_GeomFromText(:point, 4326),
                :dataReferenceDate,
                :latitude,
                :longitude,
                :address
            )
            """)
    void insertClothingBin(String managementCompany, String point, LocalDate dataReferenceDate, BigDecimal latitude, BigDecimal longitude, String address);

    @Transactional
    @Modifying
    @Query(nativeQuery = true, value = """
                INSERT INTO clothing_bin (
                    management_company,
                    location,
                    data_reference_date,
                    latitude,
                    longitude,
                    address
                ) VALUES (:#{#clothingBinEntities[0].managementCompany}, ST_GeomFromText('POINT(:#{#clothingBinEntities[0].longitude} :#{#clothingBinEntities[0].latitude})', 4326), :#{#clothingBinEntities[0].dataReferenceDate}, :#{#clothingBinEntities[0].latitude}, :#{#clothingBinEntities[0].longitude}, :#{#clothingBinEntities[0].address})
            """)
    void insertMultipleClothingBins(List<ClothingBinEntity> clothingBinEntities);
}
