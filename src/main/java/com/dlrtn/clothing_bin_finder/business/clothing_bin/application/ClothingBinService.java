package com.dlrtn.clothing_bin_finder.business.clothing_bin.application;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.api.request.GetClothingBinRequest;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.api.response.ClothingBinResponse;
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
        List<String[]> rows = csvFileReader.readCsvFile(file);
        for (String[] row : rows) {
            ClothingBinEntity entity = csvDataMapper.mapToEntity(row);
            clothingBinEntitySaver.save(entity);
        }
    }

    public List<ClothingBinResponse> read(GetClothingBinRequest request) {
        return clothingBinRepository.findAllByLocation(
                        request.latitude(),
                        request.longitude(),
                        request.distance())
                .stream()
                .map(ClothingBinEntity::toDomain)
                .map(ClothingBinResponse::fromDomain)
                .toList();
    }
}
