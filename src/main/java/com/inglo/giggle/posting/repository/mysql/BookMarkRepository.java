package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.posting.domain.BookMark;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>{

    @Query("SELECT b FROM BookMark b " +
            "WHERE b.user.id = :userId AND b.jobPosting.id = :jobPostingId")
    Optional<BookMark> findByUserIdAndJobPostingId(@Param("userId") UUID userId, @Param("jobPostingId") Long jobPostingId);
}
