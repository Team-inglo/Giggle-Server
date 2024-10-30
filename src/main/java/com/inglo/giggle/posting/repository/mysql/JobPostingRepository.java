package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.posting.domain.JobPosting;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface JobPostingRepository extends JpaRepository<JobPosting, Long>{

    List<JobPosting> findAllByOwner(Owner owner);

    @Query("SELECT jp FROM JobPosting jp " +
            "JOIN UserOwnerJobPosting uojp ON uojp.jobPosting.id = uojp.jobPosting.id " +
            "JOIN Document d ON d.userOwnerJobPosting.id = uojp.id " +
            "WHERE d.id = :documentId"
    )
    Optional<JobPosting> findByDocumentId(@Param("documentId") Long documentId);
}
