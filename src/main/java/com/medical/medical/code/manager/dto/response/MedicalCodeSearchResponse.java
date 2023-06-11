package com.medical.medical.code.manager.dto.response;

import lombok.Data;
import com.medical.medical.code.manager.dto.MedicalCodeDTO;

import java.util.List;

@Data
public class MedicalCodeSearchResponse {

  List<MedicalCodeDTO> medicalCodes;
  private int size;
  private int currentPage;
  private long totalItems;
  private int totalPages;
}
