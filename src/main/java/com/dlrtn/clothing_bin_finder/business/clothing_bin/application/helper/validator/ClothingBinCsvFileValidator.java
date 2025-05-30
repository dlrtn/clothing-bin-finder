package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper.validator;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Component
public class ClothingBinCsvFileValidator {

    private static final int MINIMUM_COLUMNS = 9;
    private static final Set<String> acceptedTypes = Set.of("text/csv", "application/csv", "application/vnd.ms-excel");


    public void validateNotEmptyFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("CSv file is empty");
        }
    }

    public void validateCsvContentType(String contentType) {
        if (contentType == null || !acceptedTypes.contains(contentType)) {
            throw new IllegalArgumentException("Unsupported file format: " + (contentType != null ? contentType : "unknown"));
        }
    }

    public void validateRowLength(String[] line) {
        if (line.length < MINIMUM_COLUMNS) {
            throw new IllegalArgumentException("CSV row does not contain the required number of columns (minimum: " + MINIMUM_COLUMNS + ")");
        }
    }

    public void validateRowsNotEmpty(List<String[]> rows) {
        if (rows.isEmpty()) {
            throw new IllegalArgumentException("CSV file must contain at least one data row");
        }
    }
}
