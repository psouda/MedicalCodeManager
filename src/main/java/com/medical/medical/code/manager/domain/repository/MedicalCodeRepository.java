package com.medical.medical.code.manager.domain.repository;

import com.medical.medical.code.manager.domain.entity.MedicalCode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MedicalCodeRepository extends CrudRepository<MedicalCode, Long> {

  Page<MedicalCode> findBySourceContainingAndCodeListCodeContainingAndCodeContaining(
          String source, String codeListCode, String code, Pageable pageable);
  Optional<MedicalCode> findByCode(String code);
}
