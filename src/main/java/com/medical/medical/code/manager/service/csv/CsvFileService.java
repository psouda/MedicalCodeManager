package com.medical.medical.code.manager.service.csv;

import com.medical.medical.code.manager.dto.MedicalCodeDTO;
import com.medical.medical.code.manager.exception.CSVFileParsingException;
import com.opencsv.bean.CsvToBean;
import com.opencsv.bean.CsvToBeanBuilder;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
public class CsvFileService {

  public List<MedicalCodeDTO> parseCsvFile(MultipartFile file) {
    try (BufferedReader fileReader =
        new BufferedReader(new InputStreamReader(file.getInputStream(), StandardCharsets.UTF_8))) {
      CsvToBean<MedicalCodeDTO> csvToBean =
          new CsvToBeanBuilder<MedicalCodeDTO>(fileReader)
              .withType(MedicalCodeDTO.class)
              .withIgnoreLeadingWhiteSpace(true)
              .build();
      return csvToBean.parse();
    } catch (IOException exception) {
      throw new CSVFileParsingException("Failed to parse CSV file: " + exception.getMessage(), exception);
    }
  }
}
