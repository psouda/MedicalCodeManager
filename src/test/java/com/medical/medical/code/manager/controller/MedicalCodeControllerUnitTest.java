package com.medical.medical.code.manager.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import java.util.List;

import com.medical.medical.code.manager.dto.response.MedicalCodeSearchResponse;
import com.medical.medical.code.manager.dto.MedicalCodeDTO;
import com.medical.medical.code.manager.service.MedicalCodeService;
import com.medical.medical.code.manager.service.csv.CsvFileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.multipart.MultipartFile;

@SpringBootTest
class MedicalCodeControllerUnitTest {

    private MockMvc mockMvc;

    @InjectMocks
    private MedicalCodeController medicalCodeController;

    @Mock
    private MedicalCodeService medicalCodeService;

    @Mock
    private CsvFileService csvFileService;

    @BeforeEach
    public void setUp(WebApplicationContext webApplicationContext) {
        mockMvc = MockMvcBuilders.standaloneSetup(medicalCodeController).build();
    }

    @Test
    void testUploadFile() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test.csv",
                        "text/csv",
                        "source,codeListCode,code,displayValue,longDescription,fromDate,toDate,sortingPriority\nsource1,codeList1,code1,displayValue1,longDescription1,2021-01-01,2021-12-31,1"
                                .getBytes());

        List<MedicalCodeDTO> medicalCodes = List.of(new MedicalCodeDTO());
        when(csvFileService.parseCsvFile(any(MultipartFile.class))).thenReturn(medicalCodes);
        doNothing().when(medicalCodeService).saveAll(medicalCodes);

        mockMvc
                .perform(
                        multipart("/v1/medical-codes/upload")
                                .file(file)
                                .contentType(MediaType.MULTIPART_FORM_DATA))
                .andExpect(status().isCreated());

        verify(csvFileService, times(1)).parseCsvFile(any(MultipartFile.class));
        verify(medicalCodeService, times(1)).saveAll(medicalCodes);
    }

    @Test
    void testUploadFile_UnhappyPath() throws Exception {
        MockMultipartFile file =
                new MockMultipartFile(
                        "file",
                        "test.json",
                        "application/json",
                        ""
                                .getBytes());
        List<MedicalCodeDTO> codes = List.of(new MedicalCodeDTO());
        when(csvFileService.parseCsvFile(any())).thenReturn(codes);
        doNothing().when(medicalCodeService).saveAll(codes);
        mockMvc.perform(
                multipart(("/v1/medical-codes/upload"))
                        .file(file)
                        .contentType(MediaType.MULTIPART_FORM_DATA))
                        .andExpect(status().isCreated());

    }

    @Test
    void testGetAllMedicalCodes() throws Exception {
        MedicalCodeSearchResponse response = new MedicalCodeSearchResponse();
        when(medicalCodeService.searchMedicalCodes(any(), any(), any(), any())).thenReturn(response);

        mockMvc
                .perform(get("/v1/medical-codes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(medicalCodeService, times(1)).searchMedicalCodes(any(), any(), any(), any());
    }

    @Test
    void testGetByCode() throws Exception {
        MedicalCodeDTO medicalCodeDTO = new MedicalCodeDTO();
        when(medicalCodeService.getByCode(anyString())).thenReturn(medicalCodeDTO);

        mockMvc
                .perform(
                        get("/v1/medical-codes/{code}", "test-code").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        verify(medicalCodeService, times(1)).getByCode(anyString());
    }

    @Test
    void testDeleteAll() throws Exception {
        doNothing().when(medicalCodeService).deleteAll();

        mockMvc
                .perform(delete("/v1/medical-codes").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        verify(medicalCodeService, times(1)).deleteAll();
    }
}
