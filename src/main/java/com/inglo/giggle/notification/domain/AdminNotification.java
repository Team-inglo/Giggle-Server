package com.inglo.giggle.notification.domain;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.security.domain.type.ESecurityRole;
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

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "admin_notifications")
@SQLDelete(sql = "UPDATE admin_notifications SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class AdminNotification extends BaseEntity {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "title", length = 50, nullable = false)
    private String title;

    @Column(name = "message", length = 200, nullable = false)
    private String message;

    @Enumerated(EnumType.STRING)
    @Column(name = "role", nullable = false)
    private ESecurityRole role;

    @Column(name = "is_advertisement", nullable = false)
    private Boolean isAdvertisement;

    /* -------------------------------------------- */
    /* Method ------------------------------------ */
    /* -------------------------------------------- */

    @Builder
    public AdminNotification(
            String title,
            String message,
            ESecurityRole role,
            Boolean isAdvertisement
    ) {
        this.title = title;
        this.message = message;
        this.role = role;
        this.isAdvertisement = isAdvertisement;
    }
}
