package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.posting.domain.JobPosting;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{

    List<JobPosting> findAllByOwner(Owner owner);

    @EntityGraph(attributePaths = {"owner"})
    Optional<JobPosting> findWithOwnerById(Long jobPostingId);
}
