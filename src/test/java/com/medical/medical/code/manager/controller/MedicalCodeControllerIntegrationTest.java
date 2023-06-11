package com.medical.medical.code.manager.controller;

import static org.assertj.core.api.Assertions.assertThat;

import com.medical.medical.code.manager.dto.response.MedicalCodeSearchResponse;
import com.medical.medical.code.manager.dto.MedicalCodeDTO;
import com.medical.medical.code.manager.dto.response.Response;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class MedicalCodeControllerIntegrationTest {

    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Test
    void testMedicalCodeController() {
        String baseUrl = "http://localhost:" + port + "/v1/medical-codes";

        // Test file upload
        MultiValueMap<String, Object> fileMap = new LinkedMultiValueMap<>();
        fileMap.add("file", new ClassPathResource("data/exercise.csv"));
        HttpHeaders fileHeaders = new HttpHeaders();
        fileHeaders.setContentType(MediaType.MULTIPART_FORM_DATA);
        HttpEntity<MultiValueMap<String, Object>> fileRequestEntity =
                new HttpEntity<>(fileMap, fileHeaders);
        ResponseEntity<Response> fileUploadResponse =
                restTemplate.postForEntity(baseUrl + "/upload", fileRequestEntity, Response.class);
        assertThat(fileUploadResponse.getStatusCode()).isEqualTo(HttpStatus.CREATED);

        // Test getAllMedicalCodes
        ResponseEntity<Response<MedicalCodeSearchResponse>> getAllResponse =
                restTemplate.exchange(
                        baseUrl,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Response<MedicalCodeSearchResponse>>() {
                        });
        assertThat(getAllResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getAllResponse.getBody()).isNotNull();
        assertThat(getAllResponse.getBody().getData()).isNotNull();
        assertThat(getAllResponse.getBody().getData().getMedicalCodes()).isNotEmpty();

        // Test getByCode
        String code = getAllResponse.getBody().getData().getMedicalCodes().get(1).getCode();
        ResponseEntity<Response<MedicalCodeDTO>> getByCodeResponse =
                restTemplate.exchange(
                        baseUrl + "/" + code,
                        HttpMethod.GET,
                        null,
                        new ParameterizedTypeReference<Response<MedicalCodeDTO>>() {
                        });
        assertThat(getByCodeResponse.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(getByCodeResponse.getBody()).isNotNull();
        assertThat(getByCodeResponse.getBody().getData()).isNotNull();
        assertThat(getByCodeResponse.getBody().getData().getCode()).isEqualTo(code);

        // Test deleteAll
        ResponseEntity<Response<Void>> deleteAllResponse =
                restTemplate.exchange(
                        baseUrl, HttpMethod.DELETE, null, new ParameterizedTypeReference<Response<Void>>() {
                        });
        assertThat(deleteAllResponse.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}
