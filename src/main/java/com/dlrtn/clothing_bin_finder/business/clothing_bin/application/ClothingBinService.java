package com.dlrtn.clothing_bin_finder.business.clothing_bin.application;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.api.dto.request.GetClothingBinRequest;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.api.dto.response.ClothingBinResponse;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper.ClothingBinCsvFileReader;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper.ClothingBinEntitySaver;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper.CsvDataMapper;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.ClothingBinRepository;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.infra.repository.entity.ClothingBinEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ClothingBinService {

    private final ClothingBinCsvFileReader csvFileReader;
    private final CsvDataMapper csvDataMapper;
    private final ClothingBinEntitySaver clothingBinEntitySaver;
    private final ClothingBinRepository clothingBinRepository;

    public void saveFromCsvFile(MultipartFile file) {
        List<String[]> csvRows = csvFileReader.readCsvFile(file);

        List<ClothingBinEntity> clothingBinEntities = csvDataMapper.mapToClothingBinEntity(csvRows);

        clothingBinEntitySaver.saveAll(clothingBinEntities);
    }

    public List<ClothingBinResponse> read(GetClothingBinRequest request) {
        return clothingBinRepository.findAllByLocation(request.toQuery())
                .stream()
                .map(ClothingBinEntity::toDomain)
                .map(ClothingBinResponse::fromDomain)
                .toList();
    }
}
