package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.ContractWorkDayTime;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContractWorkDayTimeJpaRepository extends JpaRepository<ContractWorkDayTime, Long>{
    List<ContractWorkDayTime> findByStandardLaborContractId(Long standardLaborContractId);
}
