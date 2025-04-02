package com.inglo.giggle.resume.persistence.repository.mysql;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.persistence.entity.EducationEntity;
import com.inglo.giggle.resume.persistence.entity.ResumeEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EducationJpaRepository extends JpaRepository<EducationEntity, Long>{
    @EntityGraph(attributePaths = {"schoolEntity"})
    @Query("SELECT e FROM EducationEntity e " +
            "JOIN ResumeEntity r ON e.resumeEntity.accountId = r.accountId " +
            "WHERE e.educationLevel = :educationLevel AND r.accountId = :userId"
    )
    List<EducationEntity> findEducationByAccountIdAndEducationLevel(@Param("userId")UUID userId, @Param("educationLevel") EEducationLevel educationLevel);

    @EntityGraph(attributePaths = {"schoolEntity"})
    List<EducationEntity> findAllByResumeEntity(ResumeEntity resumeEntity);

    @EntityGraph(attributePaths = {"schoolEntity"})
    Optional<EducationEntity> findWithSchoolById(Long id);
}
