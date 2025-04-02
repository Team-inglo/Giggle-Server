package com.inglo.giggle.posting.persistence.repository.mysql;

import com.inglo.giggle.account.persistence.entity.UserEntity;
import com.inglo.giggle.posting.persistence.entity.BookMarkEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BookMarkJpaRepository extends JpaRepository<BookMarkEntity, Long>{

    @Query("SELECT b FROM BookMarkEntity b " +
            "WHERE b.userEntity.id = :userId AND b.jobPostingEntity.id = :jobPostingId")
    Optional<BookMarkEntity> findByUserIdAndJobPostingId(@Param("userId") UUID userId, @Param("jobPostingId") Long jobPostingId);

    @Query("SELECT b FROM BookMarkEntity b " +
            "JOIN FETCH b.jobPostingEntity jp " +
            "WHERE b.userEntity = :user " +
            "AND (jp.recruitmentDeadLine IS NULL OR jp.recruitmentDeadLine > CURRENT_DATE) " +
            "ORDER BY jp.recruitmentDeadLine ASC")
    Page<BookMarkEntity> findWithOwnerAndWorkDaysTimesByUser(
            @Param("user") UserEntity user,
            Pageable pageable
    );


    List<BookMarkEntity> findByUserEntity(UserEntity userEntity);
}
