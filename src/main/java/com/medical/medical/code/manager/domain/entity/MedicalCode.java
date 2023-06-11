package com.medical.medical.code.manager.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.Hibernate;

import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "medical_codes")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class MedicalCode extends BaseEntity {
  @Id
  @GeneratedValue(strategy = GenerationType.AUTO)
  @Column(name = "id")
  private Long id;

  @Column(name = "source")
  private String source;

  @Column(name = "code_list_code")
  private String codeListCode;

  @NotNull
  @Column(name = "code", unique = true)
  private String code;

  @Size(max = 255)
  @Column(name = "display_value")
  private String displayValue;

  @Size(max = 1000)
  @Column(name = "long_description")
  private String longDescription;

  @Column(name = "from_date")
  private LocalDate fromDate;

  @Column(name = "to_date")
  private LocalDate toDate;

  @Column(name = "sorting_priority")
  private Integer sortingPriority;

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
    MedicalCode that = (MedicalCode) o;
    return getId() != null && Objects.equals(getId(), that.getId());
  }

  @Override
  public int hashCode() {
    return getClass().hashCode();
  }

}
