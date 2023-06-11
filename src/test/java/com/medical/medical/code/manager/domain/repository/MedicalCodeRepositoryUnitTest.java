package com.medical.medical.code.manager.domain.repository;

import com.medical.medical.code.manager.domain.entity.MedicalCode;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

import java.time.LocalDate;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MedicalCodeRepositoryUnitTest {

    @Autowired
    private MedicalCodeRepository medicalCodeRepository;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
        medicalCodeRepository.deleteAll();
    }

    @Test
    void testFindBySourceContainingAndCodeListCodeContainingAndCodeContaining() {
        // Given
        MedicalCode medicalCode =
                MedicalCode.builder()
                        .source("TestSource")
                        .codeListCode("TestCodeListCode")
                        .code("TestCode")
                        .displayValue("TestDisplayValue")
                        .longDescription("TestLongDescription")
                        .fromDate(LocalDate.now())
                        .toDate(LocalDate.now().plusDays(30))
                        .sortingPriority(1)
                        .build();

        medicalCodeRepository.save(medicalCode);

        // When
        Page<MedicalCode> result =
                medicalCodeRepository.findBySourceContainingAndCodeListCodeContainingAndCodeContaining(
                        "Test", "Test", "Test", PageRequest.of(0, 10));

        // Then
        assertEquals(1, result.getTotalElements());
        assertEquals(medicalCode, result.getContent().get(0));
    }

    @Test
    void testFindByCode() {
        // Given
        MedicalCode medicalCode =
                MedicalCode.builder()
                        .source("TestSource")
                        .codeListCode("TestCodeListCode")
                        .code("TestCode")
                        .displayValue("TestDisplayValue")
                        .longDescription("TestLongDescription")
                        .fromDate(LocalDate.now())
                        .toDate(LocalDate.now().plusDays(30))
                        .sortingPriority(1)
                        .build();

        medicalCodeRepository.save(medicalCode);

        // When
        Optional<MedicalCode> result = medicalCodeRepository.findByCode("TestCode");

        // Then
        assertTrue(result.isPresent());
        assertEquals(medicalCode, result.get());
    }
}
