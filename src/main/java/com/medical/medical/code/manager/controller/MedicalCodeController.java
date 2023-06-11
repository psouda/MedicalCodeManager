package com.medical.medical.code.manager.controller;

import static java.time.LocalDateTime.now;

import com.medical.medical.code.manager.dto.MedicalCodeDTO;
import com.medical.medical.code.manager.dto.response.MedicalCodeSearchResponse;
import com.medical.medical.code.manager.dto.response.Response;
import com.medical.medical.code.manager.service.MedicalCodeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import com.medical.medical.code.manager.service.csv.CsvFileService;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/v1/medical-codes")
@RequiredArgsConstructor
@Tag(name = "Medical Codes", description = "API for managing medical codes")
public class MedicalCodeController {
  private final MedicalCodeService medicalCodeService;
  private final CsvFileService csvFileService;

  @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "201", description = "File uploaded successfully"),
        @ApiResponse(responseCode = "400", description = "Invalid file format")
      })
  public Response<Void> uploadFile(@RequestParam("file") MultipartFile file) {
    List<MedicalCodeDTO> medicalCodes = csvFileService.parseCsvFile(file);
    medicalCodeService.saveAll(medicalCodes);
    return Response.<Void>builder().status(HttpStatus.CREATED).timeStamp(now()).build();
  }

  @GetMapping
  @Operation(summary = "Get all medical codes")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Medical codes fetched successfully")
      })
  public Response<MedicalCodeSearchResponse> getAllMedicalCodes(
      @RequestParam(value = "source", defaultValue = "", required = false) String source,
      @RequestParam(value = "codeListCode", defaultValue = "", required = false)
          String codeListCode,
      @RequestParam(value = "code", defaultValue = "", required = false) String code,
      @RequestParam(defaultValue = "0") int page,
      @RequestParam(defaultValue = "10") int size) {
    return Response.<MedicalCodeSearchResponse>builder()
        .data(
            medicalCodeService.searchMedicalCodes(
                source, codeListCode, code, PageRequest.of(page, size)))
        .status(HttpStatus.OK)
        .timeStamp(now())
        .build();
  }

  @GetMapping("/{code}")
  @Operation(summary = "Get medical code by code")
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "200", description = "Medical code found"),
        @ApiResponse(responseCode = "404", description = "Medical code not found")
      })
  public Response<MedicalCodeDTO> getByCode(@PathVariable("code") String code) {
    return Response.<MedicalCodeDTO>builder()
        .data(medicalCodeService.getByCode(code))
        .status(HttpStatus.OK)
        .timeStamp(now())
        .build();
  }

  @DeleteMapping
  @Operation(summary = "Delete all medical codes")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  @ApiResponses(
      value = {
        @ApiResponse(responseCode = "204", description = "Medical codes deleted successfully")
      })
  public Response<Void> deleteAll() {
    medicalCodeService.deleteAll();
    return Response.<Void>builder().status(HttpStatus.NO_CONTENT).timeStamp(now()).build();
  }
}
