package com.inglo.giggle.career.repository;

import com.inglo.giggle.career.domain.BookMarkCareer;

import java.util.UUID;

public interface BookMarkCareerRepository {

    boolean existsByCareerIdAndUserId(Long careerId, UUID userId);

    void save(BookMarkCareer bookMarkCareer);

    void deleteByCareerIdAndUserId(Long careerId, UUID accountId);
}
