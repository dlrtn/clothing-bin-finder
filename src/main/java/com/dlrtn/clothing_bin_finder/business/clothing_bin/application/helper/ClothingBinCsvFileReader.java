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
        List<String[]> rows = new ArrayList<>();
        try (Reader reader = new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
            CSVReader csvReader = new CSVReaderBuilder(reader).withSkipLines(1).build();

            String[] line;
            while ((line = csvReader.readNext()) != null) {
                rows.add(line);
            }
        } catch (IOException | CsvValidationException e) {
            throw new RuntimeException("CSV 파일 처리 중 오류 발생", e);
        }

        return rows;
    }
}
