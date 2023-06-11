package com.medical.medical.code.manager.service.csv;

import com.opencsv.bean.AbstractBeanField;
import com.opencsv.exceptions.CsvDataTypeMismatchException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class LocalDateConverter extends AbstractBeanField<LocalDate, LocalDate> {
  private static final DateTimeFormatter DATE_FORMAT = DateTimeFormatter.ofPattern("dd-MM-yyyy");

  @Override
  protected Object convert(String value) throws CsvDataTypeMismatchException {
    if (value == null || value.isEmpty()) {
      return null;
    }
    try {
      return LocalDate.parse(value, DATE_FORMAT);
    } catch (DateTimeParseException e) {
      throw new CsvDataTypeMismatchException(value, LocalDate.class, "Invalid date format");
    }
  }
}
