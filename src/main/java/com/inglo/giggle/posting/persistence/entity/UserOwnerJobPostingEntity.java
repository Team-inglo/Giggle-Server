package com.inglo.giggle.posting.persistence.entity;

import com.inglo.giggle.core.persistence.BaseEntity;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.UUID;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "user_owner_job_postings")
@SQLDelete(sql = "UPDATE user_owner_job_postings SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class UserOwnerJobPostingEntity extends BaseEntity {

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

    @Column(name = "memo", length = 200)
    private String memo;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Column(name = "owner_id", nullable = false)
    private UUID ownerId;

    @Column(name = "job_posting_id", nullable = false)
    private Long jobPostingId;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public UserOwnerJobPostingEntity(
            Long id,
            EApplicationStep step,
            LocalDate lastStepUpdated,
            Boolean result,
            String feedback,
            String memo,
            UUID userId,
            UUID ownerId,
            Long jobPostingId
    ) {
        this.id = id;
        this.step = step;
        this.lastStepUpdated = lastStepUpdated;
        this.result = result;
        this.feedback = feedback;
        this.memo = memo;
        this.userId = userId;
        this.ownerId = ownerId;
        this.jobPostingId = jobPostingId;
    }
}