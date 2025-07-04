package com.inglo.giggle.resume.repository.mysql;

import com.inglo.giggle.resume.domain.BookMarkResume;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;

public interface BookMarkResumeJpaRepository extends JpaRepository<BookMarkResume, Long> {

    @Query("SELECT b FROM BookMarkResume b " +
            "WHERE b.resume.accountId = :resumeId AND b.owner.id = :ownerId")
    Optional<BookMarkResume> findByOwnerIdAndResumeId(
            @Param("ownerId") UUID ownerId,
            @Param("resumeId") UUID resumeId
    );

    @Query("""
        SELECT bmr FROM BookMarkResume bmr
        JOIN FETCH bmr.resume r
        WHERE r.accountId IN :resumeAccountIds
    """)
    List<BookMarkResume> findAllByResumeAccountIdIn(@Param("resumeAccountIds") Set<UUID> resumeAccountIds);
}
