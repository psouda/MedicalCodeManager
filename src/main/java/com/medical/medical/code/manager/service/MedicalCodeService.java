package com.medical.medical.code.manager.service;

import java.util.List;

import com.medical.medical.code.manager.dto.MedicalCodeDTO;
import com.medical.medical.code.manager.dto.response.MedicalCodeSearchResponse;
import com.medical.medical.code.manager.exception.NotFoundException;
import com.medical.medical.code.manager.service.mapper.MedicalCodeMapper;
import com.medical.medical.code.manager.service.mapper.MedicalCodeSearchMapper;
import lombok.RequiredArgsConstructor;
import com.medical.medical.code.manager.domain.repository.MedicalCodeRepository;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class MedicalCodeService {

  private final MedicalCodeRepository medicalCodeRepository;
  private final MedicalCodeSearchMapper medicalCodeSearchMapper;
  private final MedicalCodeMapper medicalCodeMapper;

  @Transactional
  public void saveAll(List<MedicalCodeDTO> medicalCodes) {
    var entities = medicalCodes.stream().map(medicalCodeMapper::toMedicalCode).toList();
    medicalCodeRepository.saveAll(entities);
  }

  @Transactional(readOnly = true)
  public MedicalCodeSearchResponse searchMedicalCodes(String source, String codeListCode, String code, PageRequest of) {
    var page = medicalCodeRepository.findBySourceContainingAndCodeListCodeContainingAndCodeContaining(source, codeListCode, code, of);
    return medicalCodeSearchMapper.toMedicalCodeSearchResponse(page);
  }

  @Transactional(readOnly = true)
  public MedicalCodeDTO getByCode(String code) {
    var medicalCode = medicalCodeRepository.findByCode(code).orElseThrow(NotFoundException::new);
    return medicalCodeMapper.toMedicalCodeDTO(medicalCode);
  }

  @Transactional
  public void deleteAll() {
    medicalCodeRepository.deleteAll();
  }
}
