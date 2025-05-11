package com.inglo.giggle.document.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.document.adapter.out.persistence.entity.ContractWorkDayTimeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractWorkDayTimeJpaRepository extends JpaRepository<ContractWorkDayTimeEntity, Long>{
    List<ContractWorkDayTimeEntity> findByStandardLaborContractEntityId(Long standardLaborContractId);
}
