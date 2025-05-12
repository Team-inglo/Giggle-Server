package com.inglo.giggle.owner.application.service;

import com.inglo.giggle.owner.application.port.in.command.UpdateOwnerNotificationAllowedCommand;
import com.inglo.giggle.owner.application.port.in.usecase.UpdateOwnerNotificationAllowedUseCase;
import com.inglo.giggle.owner.application.port.out.LoadOwnerPort;
import com.inglo.giggle.owner.application.port.out.UpdateOwnerPort;
import com.inglo.giggle.owner.domain.Owner;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UpdateOwnerNotificationAllowedService implements UpdateOwnerNotificationAllowedUseCase {

    private final LoadOwnerPort loadOwnerPort;
    private final UpdateOwnerPort updateOwnerPort;

    @Override
    @Transactional
    public void execute(UpdateOwnerNotificationAllowedCommand command) {

        // 계정 정보 조회
        Owner owner = loadOwnerPort.loadOwnerOrElseThrow(command.getAccountId());

        // 알림 허용 여부 업데이트
        owner.updateNotificationAllowed(command.getIsNotificationAllowed());
        updateOwnerPort.updateOwner(owner);
    }

}
