package com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity.ClothingBinEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

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
    List<ClothingBinEntity> findAllByLocation(@Param("latitude") Double latitude, @Param("longitude") Double longitude, @Param("distance") Integer distance);

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
    void insertClothingBin(@Param("managementCompany") String managementCompany,
                           @Param("point") String point,
                           @Param("dataReferenceDate") LocalDate dataReferenceDate,
                           @Param("latitude") BigDecimal latitude,
                           @Param("longitude") BigDecimal longitude,
                           @Param("address") String address);
}
