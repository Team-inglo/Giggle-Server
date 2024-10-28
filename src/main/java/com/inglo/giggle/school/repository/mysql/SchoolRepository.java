package com.inglo.giggle.school.repository.mysql;

import com.inglo.giggle.school.domain.School;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface SchoolRepository extends JpaRepository<School, Long>{

    @Query("SELECT s FROM School s " +
            "JOIN FETCH Education e ON s.id = e.school.id " +
            "JOIN FETCH Resume r ON e.resume.accountId = r.accountId " +
            "JOIN FETCH User u ON r.user.id = u.id " +
            "JOIN UserOwnerJobPosting uojp ON uojp.user.id = u.id " +
            "WHERE uojp.user.id = :userId " +
            "ORDER BY e.graduationDate DESC")
    Optional<School> findMostRecentGraduationSchoolByUserId(@Param("userId") UUID userId);

    @Query(value = "SELECT u.account_id AS userId, s.school_name AS schoolName FROM schools s " +
            "JOIN educations e ON s.id = e.school_id " +
            "JOIN resumes r ON e.resume_id = r.account_id " +  // account_id 대신 resume_id 확인
            "JOIN users u ON r.account_id = u.account_id " +
            "WHERE u.account_id IN :userIds " +
            "AND e.graduation_date = (SELECT MAX(e2.graduation_date) " +
            "                         FROM educations e2 " +
            "                         WHERE e2.resume_id = r.account_id)", nativeQuery = true)
    List<Object[]> findUserIdsWithMostRecentSchoolNames(@Param("userIds") List<UUID> userIds);


}
