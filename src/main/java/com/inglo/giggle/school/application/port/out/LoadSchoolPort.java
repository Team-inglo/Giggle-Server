package com.inglo.giggle.school.application.port.out;

import com.inglo.giggle.school.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface LoadSchoolPort {

    School loadSchoolOrElseThrow(Long id);

    List<School> loadSchools(List<Long> ids);

    Optional<School> loadSchoolOptional(UUID userId);

    List<Object[]> loadUserIdsAndSchools(List<UUID> userIds);

    School loadSchoolOrElseThrow(String schoolName);

    Page<School> loadSchools(Pageable pageable, String search);
}
