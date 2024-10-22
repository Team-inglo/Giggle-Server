package com.inglo.giggle.document.repository.mysql;

import com.inglo.giggle.document.domain.Reject;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface RejectRepository extends JpaRepository<Reject, Long>{
}
