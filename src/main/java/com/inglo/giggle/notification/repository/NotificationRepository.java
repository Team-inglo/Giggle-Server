package com.inglo.giggle.notification.repository;

import com.inglo.giggle.notification.domain.Notification;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.UUID;

public interface NotificationRepository {

    Notification findByIdOrElseThrow(Long id);

    Page<Notification> findByUserOwnerJobPostingUserId(UUID userId, Pageable pageable);

    Page<Notification> findByUserOwnerJobPostingOwnerId(UUID ownerId, Pageable pageable);

    void save(Notification notification);
}
