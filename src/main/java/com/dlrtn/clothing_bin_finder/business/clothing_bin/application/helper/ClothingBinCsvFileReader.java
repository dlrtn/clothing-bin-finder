package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ClothingBinCsvFileReader {

    private final ClothingBinCsvFileValidator csvFileValidator;

    public List<String[]> readCsvFile(MultipartFile file) {
        csvFileValidator.validateNotEmptyFile(file);
        csvFileValidator.validateCsvContentType(file.getContentType());

        List<String[]> rows;
        try {
            rows = parseCsvFile(file);
        } catch (IOException | CsvValidationException e) {
            throw new IllegalArgumentException("CSV 파일 처리 중 오류 발생: " + e.getMessage(), e);
        }

        csvFileValidator.validateRowsNotEmpty(rows);
        return rows;
    }

    private List<String[]> parseCsvFile(MultipartFile file) throws IOException, CsvValidationException {
        List<String[]> rows = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8));
             CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                csvFileValidator.validateRowLength(line);
                rows.add(line);
            }
        }
        return rows;
    }


}
