package com.inglo.giggle.notification.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
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
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.SQLRestriction;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notifications")
@SQLDelete(sql = "UPDATE notifications SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class Notification extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "message", length = 100, nullable = false)
    private String message;

    @Column(name = "is_read", nullable = false)
    private Boolean isRead;

    @Enumerated(EnumType.STRING)
    @Column(name = "notification_type", nullable = false)
    private ENotificationType notificationType;

    /* -------------------------------------------- */
    /* Many To One Mapping ------------------------ */
    /* -------------------------------------------- */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_owner_job_postings_id", nullable = false)
    private UserOwnerJobPosting userOwnerJobPosting;

    @Builder
    public Notification(
            String message,
            UserOwnerJobPosting userOwnerJobPosting,
            ENotificationType notificationType
    ) {
        this.message = message;
        this.isRead = false;
        this.userOwnerJobPosting = userOwnerJobPosting;
        this.notificationType = notificationType;
    }

    public void updateIsRead() {
        this.isRead = true;
    }
}
