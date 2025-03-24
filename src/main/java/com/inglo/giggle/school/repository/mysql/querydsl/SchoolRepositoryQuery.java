package com.inglo.giggle.school.repository.mysql.querydsl;

import com.inglo.giggle.school.domain.School;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface SchoolRepositoryQuery {

    Page<School> findAllBySearch(Pageable pageable, String search);
}
