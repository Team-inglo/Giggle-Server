package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserOwnerJobPostingRepository extends JpaRepository<UserOwnerJobPosting, Long>{

    @EntityGraph(attributePaths = {"jobPosting"})
    Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUser(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPosting", "owner"})
    Page<UserOwnerJobPosting> findAllPagedWithJobPostingAndOwnerByUser(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPosting"})
    Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndStep(User user, EApplicationStep step, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPosting", "owner", "user","jobPosting.workDayTimes"})
    Optional<UserOwnerJobPosting> findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesById(Long id);

    @EntityGraph(attributePaths = {"jobPosting"})
    List<UserOwnerJobPosting> findAllWithJobPostingByUser(User user);

    @EntityGraph(attributePaths = {"jobPosting"})
    Optional<UserOwnerJobPosting> findWithJobPostingById(Long id);

    @EntityGraph(attributePaths = {"jobPosting"})
    Page<UserOwnerJobPosting> findWithJobPostingByOwner(Owner owner, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPosting", "owner", "user"})
    Optional<UserOwnerJobPosting> findWithOwnerAndUserJobPostingById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Optional<UserOwnerJobPosting> findWithUserById(Long userOwnerJobPostingsId);

    @EntityGraph(attributePaths = {"jobPosting"})
    List<UserOwnerJobPosting> findAllWithJobPostingByOwner(Owner owner);

    @EntityGraph(attributePaths = {"user"})
    Page<UserOwnerJobPosting> findAllPageWithUserByJobPosting(JobPosting jobPosting, Pageable pageable);
}
