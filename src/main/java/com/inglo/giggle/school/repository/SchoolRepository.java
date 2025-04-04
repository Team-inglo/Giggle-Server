package com.inglo.giggle.school.repository;

import com.inglo.giggle.school.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface SchoolRepository {

    School findByIdOrElseThrow(Long id);

    Optional<School> findTopByUserIdOrderByGraduationDateDescOptional(UUID userId);

    List<Object[]> findUserIdsWithMostRecentSchoolNames(List<UUID> userIds);

    School findBySchoolNameOrElseThrow(String schoolName);

    Page<School> findBySchoolNameContaining(String search, Pageable pageable);

    void save(School school);

    void deleteById(Long schoolId);

    Page<School> findAllBySearch(Pageable pageable, String search);
}
