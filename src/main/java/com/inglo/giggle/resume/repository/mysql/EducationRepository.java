package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long>{

    @Query("SELECT e FROM Education e " +
            "JOIN Resume r ON e.resume.accountId = r.accountId " +
            "WHERE e.educationLevel = :educationLevel AND r.accountId = :userId"
    )
    Optional<Education> findEducationByAccountIdAndEducationLevel(@Param("userId")UUID userId, @Param("eduationLevel") EEducationLevel educationLevel);
}
