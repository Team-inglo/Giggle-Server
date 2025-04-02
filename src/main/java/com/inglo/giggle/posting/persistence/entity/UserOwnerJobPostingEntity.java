package com.inglo.giggle.posting.persistence.entity;

import com.inglo.giggle.account.persistence.entity.OwnerEntity;
import com.inglo.giggle.account.persistence.entity.UserEntity;
import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.document.persistence.entity.DocumentEntity;
import com.inglo.giggle.notification.persistence.entity.NotificationEntity;
import com.inglo.giggle.posting.domain.type.EApplicationStep;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

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
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity userEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "job_posting_id", nullable = false)
    private JobPostingEntity jobPostingEntity;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "owner_id", nullable = false)
    private OwnerEntity ownerEntity;


    /* -------------------------------------------- */
    /* One To Many Mapping ------------------------ */
    /* -------------------------------------------- */
    @OneToMany(mappedBy = "userOwnerJobPostingEntity", cascade = CascadeType.ALL)
    private List<DocumentEntity> documentEntities = new ArrayList<>();

    @OneToMany(mappedBy = "userOwnerJobPostingEntity", cascade = CascadeType.ALL)
    private List<NotificationEntity> notificationEntities = new ArrayList<>();

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public UserOwnerJobPostingEntity(
            UserEntity userEntity,
            JobPostingEntity jobPostingEntity,
            OwnerEntity ownerEntity,
            EApplicationStep step,
            LocalDate lastStepUpdated,
            Boolean result,
            String feedback,
            String memo
    ) {
        this.userEntity = userEntity;
        this.jobPostingEntity = jobPostingEntity;
        this.ownerEntity = ownerEntity;
        this.step = step;
        this.lastStepUpdated = lastStepUpdated;
        this.result = result;
        this.feedback = feedback;
        this.memo = memo;
    }

    public void updateChild(UserEntity userEntity, JobPostingEntity jobPostingEntity, OwnerEntity ownerEntity) {
        this.userEntity = userEntity;
        this.jobPostingEntity = jobPostingEntity;
        this.ownerEntity = ownerEntity;
    }
}