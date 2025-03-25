package com.inglo.giggle.term.repository.mysql;

import com.inglo.giggle.term.domain.Term;
import com.inglo.giggle.term.domain.type.ETermType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface TermJpaRepository extends JpaRepository<Term, Long> {

    Optional<Term> findTopByTermTypeOrderByCreatedAtDesc(ETermType termType);

    @Query("""
    SELECT t 
    FROM Term t
    WHERE t.termType IN :termTypes
      AND t.createdAt = (
        SELECT MAX(t2.createdAt)
        FROM Term t2
        WHERE t2.termType = t.termType
      )
""")
    List<Term> findLatestTermsByTermType(@Param("termTypes") List<ETermType> termTypes);

}
