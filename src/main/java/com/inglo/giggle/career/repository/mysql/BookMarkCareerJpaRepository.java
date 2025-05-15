package com.inglo.giggle.career.repository.mysql;

import com.inglo.giggle.career.domain.BookMarkCareer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface BookMarkCareerJpaRepository extends JpaRepository<BookMarkCareer, Long> {
    boolean existsByCareerIdAndUserId(Long careerId, UUID userId);

    void deleteByCareerIdAndUserId(Long careerId, UUID accountId);
}
