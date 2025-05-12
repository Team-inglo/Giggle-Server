package com.inglo.giggle.school.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.school.adapter.out.persistence.entity.SchoolEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchoolJpaRepository extends JpaRepository<SchoolEntity, Long> {

    @Query("SELECT s FROM SchoolEntity s " +
            "JOIN EducationEntity e ON s.id = e.schoolId " +
            "JOIN ResumeEntity r ON e.resumeEntity.accountId = r.accountId " +
            "JOIN UserEntity u ON r.accountId = u.id " +
            "WHERE u.id = :userId " +
            "ORDER BY e.graduationDate DESC")
    Optional<SchoolEntity> findTopByUserIdOrderByGraduationDateDesc(@Param("userId") UUID userId);


    @Query(value = "SELECT BIN_TO_UUID(u.account_id) AS userId, s.school_name AS schoolName FROM schools s " +
            "JOIN educations e ON s.id = e.school_id " +
            "JOIN resumes r ON e.resume_id = r.account_id " +
            "JOIN users u ON r.account_id = u.account_id " +
            "WHERE u.account_id IN :userIds " +
            "AND e.graduation_date = (SELECT MAX(e2.graduation_date) " +
            "                         FROM educations e2 " +
            "                         WHERE e2.resume_id = r.account_id)" +
            "AND s.deleted_at IS NULL ", nativeQuery = true)
    List<Object[]> findUserIdsWithMostRecentSchoolNames(@Param("userIds") List<UUID> userIds);


    Optional<SchoolEntity> findBySchoolName(String schoolName);
}
