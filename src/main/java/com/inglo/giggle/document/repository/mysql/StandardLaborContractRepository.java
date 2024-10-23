package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.StandardLaborContract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface StandardLaborContractRepository extends JpaRepository<StandardLaborContract, Long>{
}
