package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Component
public class ClothingBinCsvFileValidator {

    private static final int MINIMUM_COLUMNS = 9;

    public void validateNotEmptyFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("CSV 파일이 비어 있습니다");
        }
    }

    public void validateCsvContentType(String contentType) {
        if (contentType == null || !contentType.equals("text/csv") && !contentType.equals("application/csv")) {
            throw new IllegalArgumentException("CSV 파일 형식이 아닙니다: " + (contentType != null ? contentType : "unknown"));
        }
    }

    public void validateRowLength(String[] line) {
        if (line.length < MINIMUM_COLUMNS) {
            throw new IllegalArgumentException("CSV 행이 필요한 열 수를 포함하지 않습니다");
        }
    }

    public void validateRowsNotEmpty(List<String[]> rows) {
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("CSV 파일에는 최소 한 개 이상의 데이터 행이 필요합니다");
        }
    }
}
