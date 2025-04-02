package com.inglo.giggle.termEntity.persistence.repository.mysql;

import com.inglo.giggle.term.domain.type.ETermType;
import com.inglo.giggle.term.persistence.entity.TermEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TermJpaRepository extends JpaRepository<TermEntity, Long> {

    Optional<TermEntity> findTopByTermTypeOrderByCreatedAtDesc(ETermType termType);

    @Query("""
    SELECT t 
    FROM TermEntity t
    WHERE t.termType IN :termTypes
      AND t.createdAt = (
        SELECT MAX(t2.createdAt)
        FROM TermEntity t2
        WHERE t2.termType = t.termType
      )
""")
    List<TermEntity> findLatestTermsByTermType(@Param("termTypes") List<ETermType> termTypes);

}
