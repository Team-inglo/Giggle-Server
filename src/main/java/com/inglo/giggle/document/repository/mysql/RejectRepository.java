package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.Reject;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RejectRepository extends JpaRepository<Reject, Long>{
}
