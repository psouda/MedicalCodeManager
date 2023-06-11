package com.medical.medical.code.manager.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

import java.util.List;

import com.medical.medical.code.manager.domain.entity.MedicalCode;
import com.medical.medical.code.manager.dto.response.MedicalCodeSearchResponse;
import com.medical.medical.code.manager.service.mapper.MedicalCodeMapper;
import com.medical.medical.code.manager.service.mapper.MedicalCodeSearchMapper;
import com.medical.medical.code.manager.domain.repository.MedicalCodeRepository;
import com.medical.medical.code.manager.service.mapper.MedicalCodeMapperImpl;
import com.medical.medical.code.manager.service.mapper.MedicalCodeSearchMapperImpl;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

@SpringBootTest
class MedicalCodeAndCSVServiceTest {

  @Mock private MedicalCodeRepository medicalCodeRepository;

  @InjectMocks private MedicalCodeService medicalCodeService;

  @Spy private MedicalCodeMapper medicalCodeMapper = new MedicalCodeMapperImpl();

  @Spy
  private MedicalCodeSearchMapper medicalCodeSearchMapper =
      new MedicalCodeSearchMapperImpl(medicalCodeMapper);

  @BeforeEach
  void setUp() {}

  @AfterEach
  void tearDown() {}

  @Test
  void testFindAllShouldReturnOneRecord() {
    Page<MedicalCode> medicalCodePage =
        new PageImpl<>(
            List.of(
                MedicalCode.builder()
                    .source("ZIB")
                    .codeListCode("ZIB001")
                    .code("271636001")
                    .displayValue("Polsslag regelmat")
                    .longDescription("The long description is necessary")
                    .build()));
    when(medicalCodeRepository.findBySourceContainingAndCodeListCodeContainingAndCodeContaining(
            any(), any(), any(), any()))
        .thenReturn(medicalCodePage);

    MedicalCodeSearchResponse actual =
            medicalCodeService.searchMedicalCodes("", "", "", PageRequest.of(0, 10));
    assertNotNull(actual);
    assertEquals(1, actual.getSize());
  }
}
