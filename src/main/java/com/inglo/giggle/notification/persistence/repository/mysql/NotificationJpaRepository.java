package com.inglo.giggle.notification.persistence.repository.mysql;

import com.inglo.giggle.notification.persistence.entity.NotificationEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("SELECT n FROM NotificationEntity n " +
            "JOIN FETCH n.userOwnerJobPostingEntity u " +
            "JOIN FETCH u.userEntity " +
            "JOIN FETCH u.jobPostingEntity " +
            "WHERE u.userEntity.id = :userId And n.notificationType = 'USER'")
    Page<NotificationEntity> findByUserOwnerJobPostingUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query("SELECT n FROM NotificationEntity n " +
            "JOIN FETCH n.userOwnerJobPostingEntity u " +
            "JOIN FETCH u.ownerEntity " +
            "JOIN FETCH u.jobPostingEntity " +
            "WHERE u.ownerEntity.id = :ownerId And n.notificationType = 'OWNER'")
    Page<NotificationEntity> findByUserOwnerJobPostingOwnerId(@Param("ownerId") UUID ownerId, Pageable pageable);


}
