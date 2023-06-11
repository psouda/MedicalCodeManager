package com.medical.medical.code.manager.service;

import com.medical.medical.code.manager.domain.entity.MedicalCode;
import com.medical.medical.code.manager.dto.MedicalCodeDTO;
import com.medical.medical.code.manager.dto.response.MedicalCodeSearchResponse;
import com.medical.medical.code.manager.exception.NotFoundException;
import com.medical.medical.code.manager.service.mapper.MedicalCodeMapper;
import com.medical.medical.code.manager.service.mapper.MedicalCodeSearchMapper;
import com.medical.medical.code.manager.domain.repository.MedicalCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.Collections;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class MedicalCodeServiceUnitTest {

  @Mock private MedicalCodeRepository medicalCodeRepository;

  @Mock private MedicalCodeSearchMapper medicalCodeSearchMapper;

  @Mock private MedicalCodeMapper medicalCodeMapper;

  @InjectMocks private MedicalCodeService medicalCodeService;

  private MedicalCode medicalCode;
  private MedicalCodeDTO medicalCodeDTO;

  @BeforeEach
  void setUp() {
    medicalCode = new MedicalCode();
    medicalCode.setCode("TestCode");

    medicalCodeDTO = new MedicalCodeDTO();
    medicalCodeDTO.setCode("TestCode");
  }

  @Test
  void testSaveAll() {
    when(medicalCodeMapper.toMedicalCode(medicalCodeDTO)).thenReturn(medicalCode);

    medicalCodeService.saveAll(Collections.singletonList(medicalCodeDTO));

    verify(medicalCodeRepository, times(1)).saveAll(Collections.singletonList(medicalCode));
  }

  @Test
  void testSearchMedicalCodes() {
    Page<MedicalCode> medicalCodePage = new PageImpl<>(Collections.singletonList(medicalCode));
    MedicalCodeSearchResponse medicalCodeSearchResponse = new MedicalCodeSearchResponse();

    when(medicalCodeRepository.findBySourceContainingAndCodeListCodeContainingAndCodeContaining(
            anyString(), anyString(), anyString(), any(PageRequest.class)))
        .thenReturn(medicalCodePage);
    when(medicalCodeSearchMapper.toMedicalCodeSearchResponse(medicalCodePage))
        .thenReturn(medicalCodeSearchResponse);

    MedicalCodeSearchResponse result =
        medicalCodeService.searchMedicalCodes("", "", "", PageRequest.of(0, 10));

    assertThat(result).isNotNull().isEqualTo(medicalCodeSearchResponse);
  }

  @Test
  void testGetByCode() {
    when(medicalCodeRepository.findByCode("TestCode")).thenReturn(Optional.of(medicalCode));
    when(medicalCodeMapper.toMedicalCodeDTO(medicalCode)).thenReturn(medicalCodeDTO);

    MedicalCodeDTO result = medicalCodeService.getByCode("TestCode");

    assertThat(result).isNotNull().isEqualTo(medicalCodeDTO);
  }

  @Test
  void testGetByCodeNotFound() {
    when(medicalCodeRepository.findByCode("TestCode")).thenReturn(Optional.empty());

    assertThatThrownBy(() -> medicalCodeService.getByCode("TestCode"))
        .isInstanceOf(NotFoundException.class);
  }

  @Test
  void testDeleteAll() {
    doNothing().when(medicalCodeRepository).deleteAll();

    medicalCodeService.deleteAll();

    verify(medicalCodeRepository, times(1)).deleteAll();
  }
}
