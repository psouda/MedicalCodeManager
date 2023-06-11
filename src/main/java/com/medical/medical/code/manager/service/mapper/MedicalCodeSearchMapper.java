package com.medical.medical.code.manager.service.mapper;

import com.medical.medical.code.manager.dto.response.MedicalCodeSearchResponse;
import com.medical.medical.code.manager.dto.MedicalCodeDTO;
import com.medical.medical.code.manager.domain.entity.MedicalCode;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.data.domain.Page;

import java.util.List;

@Mapper(
    componentModel = "spring",
    uses = MedicalCodeMapper.class,
    injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface MedicalCodeSearchMapper {

  @Mapping(target = "medicalCodes", expression = "java(page.map(medicalCodeMapper::toMedicalCodeDTO).getContent())")
  @Mapping(target = "size", expression = "java(page.getSize())")
  @Mapping(target = "currentPage", expression = "java(page.getNumber())")
  @Mapping(target = "totalItems", expression = "java(page.getTotalElements())")
  @Mapping(target = "totalPages", expression = "java(page.getTotalPages())")
  MedicalCodeSearchResponse toMedicalCodeSearchResponse(Page<MedicalCode> page);

  List<MedicalCode> toList(List<MedicalCodeDTO> transaction);
}
