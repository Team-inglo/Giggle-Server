package com.inglo.giggle.career.repository;

import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.domain.type.ECareerCategory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.UUID;

public interface CareerRepository {

    Page<Career> findCareersOrderByBookMarks(String search, List<ECareerCategory> categories, Pageable pageable);
    Page<Career> findCareersOrderByCreatedAt(String search, List<ECareerCategory> categories, Pageable pageable);
    Page<Career> findBookmarkedCareersByUser(UUID userId, Pageable pageable);

    Career findByIdOrElseThrow(Long careerId);

    void save(Career career);

    void delete(Career career);
}
