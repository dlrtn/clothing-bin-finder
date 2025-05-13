package com.dlrtn.clothing_bin_finder.business.clothing_bin.api;

import com.dlrtn.clothing_bin_finder.business.clothing_bin.api.request.GetClothingBinRequest;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.api.response.ClothingBinResponse;
import com.dlrtn.clothing_bin_finder.business.clothing_bin.application.ClothingBinService;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;


@Slf4j
@RequiredArgsConstructor
@Tag(name = "헌옷수거함", description = "헌옷수거함 도메인 관련 기능들을 지원합니다.")
@RestController
@RequestMapping("/clothing-bins")
public class ClothingBinController {

    private final ClothingBinService clothingBinService;

    @GetMapping
    public ResponseEntity<List<ClothingBinResponse>> getClothingBins(@RequestBody GetClothingBinRequest request) {
        List<ClothingBinResponse> clothingBins = clothingBinService.read(request);

        return ResponseEntity.ok(clothingBins);
    }

    @PostMapping
    public ResponseEntity<String> registerClothingBinsByCsvFile(@RequestParam("file") MultipartFile file) {
        clothingBinService.saveFromCsvFile(file);

        return ResponseEntity.ok("헌옷수거함 데이터 등록이 완료되었습니다.");
    }
}
