package com.inglo.giggle.notification.adapter.out.persistence;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.notification.adapter.out.persistence.mapper.NotificationMapper;
import com.inglo.giggle.notification.adapter.out.persistence.repository.mysql.NotificationJpaRepository;
import com.inglo.giggle.notification.application.port.out.CreateNotificationPort;
import com.inglo.giggle.notification.application.port.out.LoadNotificationPort;
import com.inglo.giggle.notification.application.port.out.UpdateNotificationPort;
import com.inglo.giggle.notification.domain.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NotificationPersistenceAdapter implements LoadNotificationPort, CreateNotificationPort, UpdateNotificationPort {

    private final NotificationJpaRepository notificationJpaRepository;
    private final NotificationMapper notificationMapper;

    @Override
    public Notification loadNotification(Long id) {
        return notificationMapper.toDomain(notificationJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_NOTIFICATION)));
    }

    @Override
    public Page<Notification> loadNotificationByUserId(UUID userId, Pageable pageable) {
        return notificationJpaRepository.findByUserOwnerJobPostingUserId(userId, pageable)
                .map(notificationMapper::toDomain);
    }

    @Override
    public Page<Notification> loadNotificationByOwnerId(UUID ownerId, Pageable pageable) {
        return notificationJpaRepository.findByUserOwnerJobPostingOwnerId(ownerId, pageable)
                .map(notificationMapper::toDomain);
    }

    @Override
    public void createNotification(Notification notification) {
        notificationJpaRepository.save(notificationMapper.toEntity(notification));
    }

    @Override
    public void updateNotification(Notification notification) {
        notificationJpaRepository.save(notificationMapper.toEntity(notification));
    }
}
