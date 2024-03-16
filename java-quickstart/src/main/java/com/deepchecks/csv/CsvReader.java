package com.deepchecks.csv;

import com.opencsv.CSVReader;
import lombok.extern.slf4j.Slf4j;

import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
public class CsvReader {

    public static final String COMMA_DELIMITER = ",";

    public List<Map<String, String>> readValues(Path filePath) {
        List<Map<String, String>> lines = new ArrayList<>();
        List<String[]> source = read(filePath);
        List<String> header = readHeader(source);

        for (int i = 1; i < source.size(); i++) {
            Map<String, String> line = new HashMap<>();
            String[] lineArray = source.get(i);
            for (int j = 0; j < lineArray.length; j++) {
                line.put(header.get(j), lineArray[j]);
            }
            lines.add(line);
        }
        return lines;
    }

    private List<String> readHeader(List<String[]> source) {
        return Arrays.asList(source.get(0));
    }

    public List<String[]> read(Path filePath) {
        try {
            log.info("Reading csv file: {}", filePath);
            List<String[]> result = readAllLines(filePath);
            log.info("File reading: SUCCESS");
            return result;
        } catch (Exception e) {
            log.error("File reading: FAILURE, filePath={}", filePath);
            throw new RuntimeException(e);
        }
    }

    public List<String[]> readAllLines(Path filePath) throws Exception {
        try (Reader reader = Files.newBufferedReader(filePath)) {
            try (CSVReader csvReader = new CSVReader(reader)) {
                return csvReader.readAll();
            }
        }
    }
}
