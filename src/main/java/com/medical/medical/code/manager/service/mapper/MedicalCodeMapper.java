package com.medical.medical.code.manager.service.mapper;

import com.medical.medical.code.manager.dto.MedicalCodeDTO;
import com.medical.medical.code.manager.domain.entity.MedicalCode;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MedicalCodeMapper {

  MedicalCodeDTO toMedicalCodeDTO(MedicalCode medicalCode);

  MedicalCode toMedicalCode(MedicalCodeDTO medicalCodeDTO);
}
