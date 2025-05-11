package com.inglo.giggle.resume.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.resume.adapter.out.persistence.entity.AdditionalLanguageEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface AdditionalLanguageJpaRepository extends JpaRepository<AdditionalLanguageEntity, Long> {
    @Query(
            "SELECT al FROM AdditionalLanguageEntity al " +
                    "JOIN FETCH al.languageSkillEntity ls " +
                    "WHERE ls.resumeId = :languageSkillId"
    )
    List<AdditionalLanguageEntity> findAllByLanguageSkillsId(@Param("languageSkillId") UUID languageSkillId);
}
