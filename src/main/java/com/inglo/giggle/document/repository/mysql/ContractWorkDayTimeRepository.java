package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.ContractWorkDayTime;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface ContractWorkDayTimeRepository extends JpaRepository<ContractWorkDayTime, Long>{
}
