package com.inglo.giggle.posting.domain;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import com.inglo.giggle.account.domain.User;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_owner_job_postings")
public class UserOwnerJobPosting {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Enumerated(EnumType.STRING)
    @Column(name = "step", nullable = false)
    private EApplicationStep step;

    @Column(name = "last_step_updated", nullable = false)
    private LocalDate lastStepUpdated;

    @Column(name = "result")
    private Boolean result;

    @Column(name = "feedback", length = 200)
    private String feedback;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDate updatedAt;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPosting jobPosting;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private Owner owner;


    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "userOwnerJobPosting", cascade = CascadeType.ALL)
    private List<Document> documents = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public UserOwnerJobPosting(User user, JobPosting jobPosting, Owner owner) {
        this.user = user;
        this.jobPosting = jobPosting;
        this.owner = owner;
        this.step = EApplicationStep.RESUME_UNDER_REVIEW;
        this.lastStepUpdated = LocalDate.now();
        this.createdAt = LocalDate.now();
        this.updatedAt = LocalDate.now();
    }

    public void updateStep(EApplicationStep step) {
        this.step = step;
        this.lastStepUpdated = LocalDate.now();
    }

    public void updateFeedback(String feedback) {
        this.feedback = feedback;
    }

    public void updateResult(Boolean result) {
        this.result = result;
    }
}