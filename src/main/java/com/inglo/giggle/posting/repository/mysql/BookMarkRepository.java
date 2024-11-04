package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.domain.BookMark;
import com.inglo.giggle.posting.domain.JobPosting;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BookMarkRepository extends JpaRepository<BookMark, Long>{

    @Query("SELECT b FROM BookMark b " +
            "WHERE b.user.id = :userId AND b.jobPosting.id = :jobPostingId")
    Optional<BookMark> findByUserIdAndJobPostingId(@Param("userId") UUID userId, @Param("jobPostingId") Long jobPostingId);

    @Query("SELECT b FROM BookMark b " +
            "JOIN FETCH b.jobPosting jp " +
            "WHERE b.user = :user " +
            "AND jp.recruitmentDeadLine > CURRENT_DATE " +
            "ORDER BY jp.recruitmentDeadLine ASC")
    Page<BookMark> findWithOwnerAndWorkDaysTimesByUser(
            @Param("user") User user,
            Pageable pageable
    );


    List<BookMark> findByUser(User user);

    List<BookMark> findByJobPosting(JobPosting jobPosting);
}
