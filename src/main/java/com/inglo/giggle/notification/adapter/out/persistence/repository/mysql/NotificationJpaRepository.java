package com.inglo.giggle.notification.adapter.out.persistence.repository.mysql;

import com.inglo.giggle.notification.adapter.out.persistence.entity.NotificationEntity;
import io.lettuce.core.dynamic.annotation.Param;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.UUID;

public interface NotificationJpaRepository extends JpaRepository<NotificationEntity, Long> {

    @Query("SELECT n FROM NotificationEntity n " +
            "JOIN UserOwnerJobPostingEntity uojp ON uojp.id = n.userOwnerJobPostingId " +
            "JOIN UserEntity u ON u.id = uojp.userId " +
            "WHERE u.id = :userId And n.notificationType = 'USER'")
    Page<NotificationEntity> findByUserOwnerJobPostingUserId(@Param("userId") UUID userId, Pageable pageable);

    @Query("SELECT n FROM NotificationEntity n " +
            "JOIN UserOwnerJobPostingEntity uojp ON uojp.id = n.userOwnerJobPostingId " +
            "JOIN OwnerEntity o ON o.id = uojp.ownerId " +
            "WHERE o.id = :ownerId And n.notificationType = 'OWNER'")
    Page<NotificationEntity> findByUserOwnerJobPostingOwnerId(@Param("ownerId") UUID ownerId, Pageable pageable);


}
