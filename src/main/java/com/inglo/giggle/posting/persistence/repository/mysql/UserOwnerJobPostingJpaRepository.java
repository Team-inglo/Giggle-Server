package com.inglo.giggle.posting.persistence.repository.mysql;

import com.inglo.giggle.owner.adapter.out.persistence.entity.OwnerEntity;
import com.inglo.giggle.user.adapter.out.persistence.entity.UserEntity;
import com.inglo.giggle.document.adapter.out.persistence.entity.DocumentEntity;
import com.inglo.giggle.posting.persistence.entity.JobPostingEntity;
import com.inglo.giggle.posting.persistence.entity.UserOwnerJobPostingEntity;
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

public interface UserOwnerJobPostingJpaRepository extends JpaRepository<UserOwnerJobPostingEntity, Long> {

    @Query("SELECT u FROM UserOwnerJobPostingEntity u WHERE :documentEntity MEMBER OF u.documentEntities")
    Optional<UserOwnerJobPostingEntity> findByDocument(@Param("document") DocumentEntity document);

    @EntityGraph(attributePaths = {"jobPostingEntity"})
    Page<UserOwnerJobPostingEntity> findAllPagedWithJobPostingByUserEntity(UserEntity userEntity, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPostingEntity", "ownerEntity"})
    Page<UserOwnerJobPostingEntity> findAllPagedWithJobPostingAndOwnerByUserEntity(UserEntity userEntity, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPostingEntity"})
    Page<UserOwnerJobPostingEntity> findAllPagedWithJobPostingByUserEntityAndStep(UserEntity userEntity, EApplicationStep step, Pageable pageable);

    @EntityGraph(attributePaths = {"jobPostingEntity"})
    @Query("SELECT u FROM UserOwnerJobPostingEntity u WHERE u.userEntity = :user AND u.step IN :steps")
    Page<UserOwnerJobPostingEntity> findAllPagedWithJobPostingByUserAndSteps(
            @Param("user") UserEntity user,
            @Param("steps") List<EApplicationStep> steps,
            Pageable pageable
    );

    @EntityGraph(attributePaths = {"jobPostingEntity", "ownerEntity", "userEntity","jobPostingEntity.workDayTimeEntities"})
    Optional<UserOwnerJobPostingEntity> findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesById(Long id);

    @EntityGraph(attributePaths = {"jobPostingEntity"})
    List<UserOwnerJobPostingEntity> findAllWithJobPostingByUserEntity(UserEntity userEntity);

    @EntityGraph(attributePaths = {"jobPostingEntity"})
    Optional<UserOwnerJobPostingEntity> findWithJobPostingById(Long id);

    @EntityGraph(attributePaths = {"jobPostingEntity", "ownerEntity"})
    Optional<UserOwnerJobPostingEntity> findWithJobPostingAndOwnerById(Long id);

    @EntityGraph(attributePaths = {"jobPostingEntity", "userEntity"})
    Optional<UserOwnerJobPostingEntity> findWithJobPostingAndUserById(Long id);

    @EntityGraph(attributePaths = {"jobPostingEntity", "ownerEntity", "userEntity"})
    Optional<UserOwnerJobPostingEntity> findWithOwnerAndUserJobPostingById(Long id);

    @EntityGraph(attributePaths = {"userEntity"})
    Optional<UserOwnerJobPostingEntity> findWithUserById(Long userOwnerJobPostingsId);

    @EntityGraph(attributePaths = {"jobPostingEntity"})
    List<UserOwnerJobPostingEntity> findAllWithJobPostingByOwnerEntity(OwnerEntity ownerEntity);

    @EntityGraph(attributePaths = {"userEntity"})
    Page<UserOwnerJobPostingEntity> findAllPageWithUserByJobPostingEntity(JobPostingEntity jobPostingEntity, Pageable pageable);

    Boolean existsByUserEntityAndJobPostingEntity(UserEntity userEntity, JobPostingEntity jobPostingEntity);

    @EntityGraph(attributePaths = {"userEntity"})
    Page<UserOwnerJobPostingEntity> findAllPageWithUserByJobPostingEntityAndStep(JobPostingEntity jobPostingEntity, EApplicationStep step, Pageable pageable);

    @EntityGraph(attributePaths = {"userEntity"})
    @Query("SELECT u FROM UserOwnerJobPostingEntity u WHERE u.jobPostingEntity = :jobPosting AND u.step IN :steps")
    Page<UserOwnerJobPostingEntity> findAllPageWithUserByJobPostingAndSteps(
            @Param("jobPosting") JobPostingEntity jobPosting,
            @Param("steps") List<EApplicationStep> steps,
            Pageable pageable
    );

    int countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    @Query("SELECT COUNT(u) FROM UserOwnerJobPostingEntity u " +
            "WHERE u.step = :step " +
            "AND u.createdAt BETWEEN :start AND :end")
    int countUserOwnerJobPostingByStepAndCreatedAtBetween(
            @Param("step") EApplicationStep step,
            @Param("start") LocalDateTime start,
            @Param("end") LocalDateTime end
    );

    @Query("SELECT uojp FROM UserOwnerJobPostingEntity uojp WHERE uojp.createdAt BETWEEN :start AND :end")
    List<UserOwnerJobPostingEntity> findAllByCreatedAtBetween(@Param("start") LocalDateTime start, @Param("end") LocalDateTime end);

    int countByStep(EApplicationStep step);
}
