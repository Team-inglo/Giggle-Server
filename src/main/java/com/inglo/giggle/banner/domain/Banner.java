package com.inglo.giggle.banner.domain;

import com.inglo.giggle.security.domain.type.ESecurityRole;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "banners")
public class Banner {

    /* -------------------------------------------- */
    /* Default Column ----------------------------- */
    /* -------------------------------------------- */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /* -------------------------------------------- */
    /* Information Column ------------------------- */
    /* -------------------------------------------- */
    @Column(name = "img_url", length = 320, nullable = false)
    private String imgUrl;

    @Lob
    @Column(name = "content", nullable = false, columnDefinition = "TEXT")
    private String content;

    @Column(name = "role", nullable = false)
    @Enumerated(EnumType.STRING)
    private ESecurityRole role;

    /* -------------------------------------------- */
    /* Timestamp Column --------------------------- */
    /* -------------------------------------------- */
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDate createdAt;

    /* -------------------------------------------- */
    /* Methods ------------------------------------ */
    /* -------------------------------------------- */
    @Builder
    public Banner(String imgUrl, String content, ESecurityRole role) {
        this.imgUrl = imgUrl;
        this.content = content;
        this.role = role;
        this.createdAt = LocalDate.now();
    }

}