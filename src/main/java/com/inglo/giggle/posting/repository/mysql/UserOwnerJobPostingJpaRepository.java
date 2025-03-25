package com.inglo.giggle.posting.repository.mysql;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface UserOwnerJobPostingJpaRepository extends JpaRepository<UserOwnerJobPosting, Long> {

    @EntityGraph(attributePaths = {"jobPosting"})
    Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUser(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPosting", "owner"})
    Page<UserOwnerJobPosting> findAllPagedWithJobPostingAndOwnerByUser(User user, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPosting"})
    Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndStep(User user, EApplicationStep step, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPosting"})
    @Query("SELECT u FROM UserOwnerJobPosting u WHERE u.user = :user AND u.step IN :steps")
    Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndSteps(
            @Param("user") User user,
            @Param("steps") List<EApplicationStep> steps,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"jobPosting", "owner", "user","jobPosting.workDayTimes"})
    Optional<UserOwnerJobPosting> findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesById(Long id);

    @EntityGraph(attributePaths = {"jobPosting"})
    List<UserOwnerJobPosting> findAllWithJobPostingByUser(User user);

    @EntityGraph(attributePaths = {"jobPosting"})
    Optional<UserOwnerJobPosting> findWithJobPostingById(Long id);

    @EntityGraph(attributePaths = {"jobPosting", "owner"})
    Optional<UserOwnerJobPosting> findWithJobPostingAndOwnerById(Long id);

    @EntityGraph(attributePaths = {"jobPosting", "user"})
    Optional<UserOwnerJobPosting> findWithJobPostingAndUserById(Long id);

    @EntityGraph(attributePaths = {"jobPosting", "owner", "user"})
    Optional<UserOwnerJobPosting> findWithOwnerAndUserJobPostingById(Long id);

    @EntityGraph(attributePaths = {"user"})
    Optional<UserOwnerJobPosting> findWithUserById(Long userOwnerJobPostingsId);

    @EntityGraph(attributePaths = {"jobPosting"})
    List<UserOwnerJobPosting> findAllWithJobPostingByOwner(Owner owner);

    @EntityGraph(attributePaths = {"user"})
    Page<UserOwnerJobPosting> findAllPageWithUserByJobPosting(JobPosting jobPosting, Pageable pageable);

    Boolean existsByUserAndJobPosting(User user, JobPosting jobPosting);

    @EntityGraph(attributePaths = {"user"})
    Page<UserOwnerJobPosting> findAllPageWithUserByJobPostingAndStep(JobPosting jobPosting, EApplicationStep step, Pageable pageable);

    @EntityGraph(attributePaths = {"user"})
    @Query("SELECT u FROM UserOwnerJobPosting u WHERE u.jobPosting = :jobPosting AND u.step IN :steps")
    Page<UserOwnerJobPosting> findAllPageWithUserByJobPostingAndSteps(
            @Param("jobPosting") JobPosting jobPosting,
            @Param("steps") List<EApplicationStep> steps,
            Pageable pageable
    );

    int countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(u) FROM UserOwnerJobPosting u " +
            "WHERE u.step = :step " +
            "AND u.createdAt BETWEEN :start AND :end")
    int countUserOwnerJobPostingByStepAndCreatedAtBetween(
            @Param("step") EApplicationStep step,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT uojp FROM UserOwnerJobPosting uojp WHERE uojp.createdAt BETWEEN :start AND :end")
    List<UserOwnerJobPosting> findAllByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    int countByStep(EApplicationStep step);
}
