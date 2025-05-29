package com.dlrtn.clothing_bin_finder.business.clothing_bin.application.helper;

import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

@Component
public class ClothingBinCsvFileReader {

    public List<String[]> readCsvFile(MultipartFile file) {
        if (file.isEmpty()) {
            throw new IllegalArgumentException("CSV 파일이 비어 있습니다");
        }

        String contentType = file.getContentType();
        if (contentType == null || !contentType.equals("text/csv") && !contentType.equals("application/csv")) {
            throw new IllegalArgumentException("CSV 파일 형식이 아닙니다: " + (contentType != null ? contentType : "unknown"));
        }

        List<String[]> rows = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            try (CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build()) {

                String[] line;
                while ((line = csvReader.readNext()) != null) {
                    if (line.length < 9) {
                        throw new IllegalArgumentException("CSV 행이 필요한 열 수를 포함하지 않습니다");
                    }
                    rows.add(line);
                }
            }
        } catch (IOException | CsvValidationException e) {
            throw new IllegalArgumentException("CSV 파일 처리 중 오류 발생: " + e.getMessage(), e);
        }

        if (rows.isEmpty()) {
            throw new IllegalArgumentException("CSV 파일에 데이터 행이 없습니다");
        }

        return rows;
    }
}
