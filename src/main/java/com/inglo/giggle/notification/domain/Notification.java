package com.inglo.giggle.notification.domain;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.core.type.ENotificationType;
import com.inglo.giggle.posting.domain.UserOwnerJobPosting;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "notifications")
public class Notification {

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

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

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
        this.createdAt = LocalDateTime.now();
    }
}
