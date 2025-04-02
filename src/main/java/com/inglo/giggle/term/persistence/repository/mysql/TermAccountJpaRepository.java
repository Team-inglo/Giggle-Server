package com.inglo.giggle.term.persistence.repository.mysql;

import com.inglo.giggle.term.persistence.entity.TermAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermAccountJpaRepository extends JpaRepository<TermAccountEntity, Long> {
}
