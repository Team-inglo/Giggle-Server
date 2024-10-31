package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.core.type.EEducationLevel;
import com.inglo.giggle.resume.domain.Education;
import com.inglo.giggle.resume.domain.Resume;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EducationRepository extends JpaRepository<Education, Long>{
    @EntityGraph(attributePaths = {"school"})
    @Query("SELECT e FROM Education e " +
            "JOIN Resume r ON e.resume.accountId = r.accountId " +
            "WHERE e.educationLevel = :educationLevel AND r.accountId = :userId"
    )
    List<Education> findEducationByAccountIdAndEducationLevel(@Param("userId")UUID userId, @Param("eduationLevel") EEducationLevel educationLevel);

    @EntityGraph(attributePaths = {"school"})
    List<Education> findAllByResume(Resume resume);

    @EntityGraph(attributePaths = {"school"})
    Optional<Education> findWithSchoolById(Long id);

    @EntityGraph(attributePaths = {"school"})
    Optional<Education> findWithSchoolByResume(Resume resume);
}
