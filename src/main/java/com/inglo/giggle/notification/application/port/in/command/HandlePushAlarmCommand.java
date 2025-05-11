package com.inglo.giggle.notification.application.port.in.command;

import com.inglo.giggle.core.dto.SelfValidating;
import com.inglo.giggle.core.type.EKafkaStatus;
import com.inglo.giggle.core.type.ENotificationType;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;

import java.util.List;

@Getter
public class HandlePushAlarmCommand extends SelfValidating<HandlePushAlarmCommand> {

    @NotNull(message = "kafka_status는 null이 될 수 없습니다.")
    private final EKafkaStatus eKafkaStatus;

    @NotNull(message = "notification_type은 null이 될 수 없습니다.")
    private final ENotificationType eNotificationType;

    private final List<String> deviceTokens;

    @NotNull(message = "title은 null이 될 수 없습니다.")
    private final String title;

    @NotNull
    private final Boolean notificationAllowed;

    public HandlePushAlarmCommand(
            EKafkaStatus eKafkaStatus,
            ENotificationType eNotificationType,
            List<String> deviceTokens,
            String title,
            Boolean notificationAllowed
    ) {
        this.eKafkaStatus = eKafkaStatus;
        this.eNotificationType = eNotificationType;
        this.deviceTokens = deviceTokens;
        this.title = title;
        this.notificationAllowed = notificationAllowed;
        this.validateSelf();
    }
}
