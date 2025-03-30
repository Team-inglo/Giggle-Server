package com.inglo.giggle.core.postConstruct;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.List;

@Component
@RequiredArgsConstructor
public class FireBasePostConstruct {

    @Value("${firebase.fcm-key-path}")
    private String fcmKeyPath;

    @Value("${firebase.fcm-key-scope}")
    private String fcmKeyScope;

    @PostConstruct
    public void init() {
        try {
            FirebaseOptions options = new FirebaseOptions.Builder()
                    .setCredentials(
                            GoogleCredentials
                                    .fromStream(new FileInputStream(fcmKeyPath))
                                    .createScoped(List.of(fcmKeyScope))
                    ).build();

            if (FirebaseApp.getApps().isEmpty())
                FirebaseApp.initializeApp(options);

        } catch (IOException e) {
            throw new CommonException(ErrorCode.FIREBASE_CONFIGURATION_FAILED);
        }
    }
}