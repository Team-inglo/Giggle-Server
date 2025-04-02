package com.inglo.giggle.posting.persistence.repository;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.posting.domain.JobPosting;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

public interface UserOwnerJobPostingRepository {

    UserOwnerJobPosting findByIdOrElseThrow(Long id);

    UserOwnerJobPosting findByDocumentOrElseThrow(Document document);

    Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUser(User user, Pageable pageable);

    Page<UserOwnerJobPosting> findAllPagedWithJobPostingAndOwnerByUser(User user, Pageable pageable);

    Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndStep(User user, EApplicationStep step, Pageable pageable);

    Page<UserOwnerJobPosting> findAllPagedWithJobPostingByUserAndSteps(
            User user,
            List<EApplicationStep> steps,
            Pageable pageable
    );

    UserOwnerJobPosting findWithJobPostingAndOwnerAndJobPostingsWorkDayTimesByIdOrElseThrow(Long id);

    List<UserOwnerJobPosting> findAllWithJobPostingByUser(User user);

    UserOwnerJobPosting findWithJobPostingByIdOrElseThrow(Long id);

    UserOwnerJobPosting findWithJobPostingAndOwnerByIdOrElseThrow(Long id);

    UserOwnerJobPosting findWithJobPostingAndUserByIdOrElseThrow(Long id);

    UserOwnerJobPosting findWithOwnerAndUserJobPostingByIdOrElseThrow(Long id);

    UserOwnerJobPosting findWithUserByIdOrElseThrow(Long userOwnerJobPostingsId);

    List<UserOwnerJobPosting> findAllWithJobPostingByOwner(Owner owner);

    Page<UserOwnerJobPosting> findAllPageWithUserByJobPosting(JobPosting jobPosting, Pageable pageable);

    Boolean existsByUserAndJobPosting(User user, JobPosting jobPosting);

    Page<UserOwnerJobPosting> findAllPageWithUserByJobPostingAndStep(JobPosting jobPosting, EApplicationStep step, Pageable pageable);

    Page<UserOwnerJobPosting> findAllPageWithUserByJobPostingAndSteps(
            JobPosting jobPosting,
            List<EApplicationStep> steps,
            Pageable pageable
    );

    int countByCreatedAtBetween(LocalDateTime start, LocalDateTime end);

    int countUserOwnerJobPostingByStepAndCreatedAtBetween(
            EApplicationStep step,
            LocalDateTime start,
            LocalDateTime end
    );

    int countByStep(EApplicationStep step);

    UserOwnerJobPosting save(UserOwnerJobPosting userOwnerJobPosting);

    void delete(UserOwnerJobPosting userOwnerJobPosting);

    Page<UserOwnerJobPosting> searchWithConditions(
            String search,
            LocalDateTime startDateTime,
            LocalDateTime endDateTime,
            String filterType,
            String filter,
            Pageable pageable
    );

    List<GraphStats> countGraphStatsByMonth(LocalDateTime startDate, LocalDateTime endDate);

    long count();

    record GraphStats(
            Integer year,
            Integer month,
            Long total,
            Long accepted,
            Long rejected,
            Long success
    ) {}
}
