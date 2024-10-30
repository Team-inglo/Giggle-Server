package com.inglo.giggle.school.repository.mysql;

import com.inglo.giggle.school.domain.School;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

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

    Optional<School> findBySchoolName(String schoolName);

    @Query("SELECT s FROM School s WHERE s.schoolName LIKE %:search%")
    Page<School> findBySchoolNameContaining(@Param("search") String search, Pageable pageable);

}
