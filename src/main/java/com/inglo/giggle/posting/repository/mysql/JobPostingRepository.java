package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.posting.domain.JobPosting;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{

    List<JobPosting> findAllByOwner(Owner owner);

    @EntityGraph(attributePaths = {"owner"})
    Optional<JobPosting> findWithOwnerById(Long jobPostingId);

    @Query("SELECT jp FROM JobPosting jp " +
            "LEFT JOIN FETCH jp.workDayTimes " +
            "LEFT JOIN FETCH jp.companyImages " +
            "WHERE jp.id = :jobPostingId")
    Optional<JobPosting> findWithWorkDayTimesAndCompanyImagesById(@Param("jobPostingId") Long jobPostingId);

    Page<JobPosting> findWithPageByOwner(Owner owner, Pageable pageRequest);
}
