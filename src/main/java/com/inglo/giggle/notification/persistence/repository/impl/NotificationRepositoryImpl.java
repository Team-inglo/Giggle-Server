package com.inglo.giggle.notification.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.notification.domain.Notification;
import com.inglo.giggle.notification.persistence.entity.NotificationEntity;
import com.inglo.giggle.notification.persistence.mapper.NotificationMapper;
import com.inglo.giggle.notification.persistence.repository.NotificationRepository;
import com.inglo.giggle.notification.persistence.repository.mysql.NotificationJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class NotificationRepositoryImpl implements NotificationRepository {

    private final NotificationJpaRepository notificationJpaRepository;

    @Override
    public Notification findByIdOrElseThrow(Long id) {
        return NotificationMapper.toDomain(notificationJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_NOTIFICATION)));
    }

    @Override
    public Page<Notification> findByUserOwnerJobPostingUserId(UUID userId, Pageable pageable) {
        return notificationJpaRepository.findByUserOwnerJobPostingUserId(userId, pageable)
                .map(NotificationMapper::toDomain);
    }

    @Override
    public Page<Notification> findByUserOwnerJobPostingOwnerId(UUID ownerId, Pageable pageable) {
        return notificationJpaRepository.findByUserOwnerJobPostingOwnerId(ownerId, pageable)
                .map(NotificationMapper::toDomain);
    }

    @Override
    public Notification save(Notification notification) {
        NotificationEntity entity = notificationJpaRepository.save(NotificationMapper.toEntity(notification));
        return NotificationMapper.toDomain(entity);
    }
}
