package com.inglo.giggle.termaccount.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.termaccount.adapter.out.persistence.entity.TermAccountEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TermAccountJpaRepository extends JpaRepository<TermAccountEntity, Long> {
}
