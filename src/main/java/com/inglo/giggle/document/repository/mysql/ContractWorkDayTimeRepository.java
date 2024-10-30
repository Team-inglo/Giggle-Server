package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.ContractWorkDayTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ContractWorkDayTimeRepository extends JpaRepository<ContractWorkDayTime, Long>{
    List<ContractWorkDayTime> findByStandardLaborContractId(Long standardLaborContractId);
}
