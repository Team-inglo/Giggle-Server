package com.inglo.giggle.banner.persistence.entity;

import com.inglo.giggle.core.dto.BaseEntity;
import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
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
@Table(name = "banners")
@SQLDelete(sql = "UPDATE banners SET deleted_at = CURRENT_TIMESTAMP WHERE id = ?")
@SQLRestriction("deleted_at IS NULL")
public class BannerEntity extends BaseEntity {

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

    @Column(name = "img_url", length = 320, nullable = false)
    private String imgUrl;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ESecurityRole role;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public BannerEntity(String title, String imgUrl, String content, ESecurityRole role) {
        this.title = title;
        this.imgUrl = imgUrl;
        this.content = content;
        this.role = role;
    }

}