package com.medical.medical.code.manager.dto;

import com.medical.medical.code.manager.service.csv.LocalDateConverter;
import com.opencsv.bean.CsvBindByName;
import com.opencsv.bean.CsvCustomBindByName;
import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MedicalCodeDTO {

  @CsvBindByName(column = "source")
  private String source;

  @CsvBindByName(column = "codeListCode")
  private String codeListCode;

  @NotNull
  @CsvBindByName(column = "code")
  private String code;

  @CsvBindByName(column = "displayValue")
  private String displayValue;

  @CsvBindByName(column = "longDescription")
  private String longDescription;

  @CsvBindByName(column = "fromDate")
  @CsvCustomBindByName(converter = LocalDateConverter.class)
  private LocalDate fromDate;

  @CsvBindByName(column = "toDate")
  @CsvCustomBindByName(converter = LocalDateConverter.class)
  private LocalDate toDate;

  @CsvBindByName(column = "sortingPriority")
  private Integer sortingPriority;
}
