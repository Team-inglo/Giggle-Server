package com.inglo.giggle.document.domain.service;

import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.domain.Reject;
import org.springframework.stereotype.Service;

@Service
public class RejectService {
    public Reject createReject(Document document, String reason) {
        return Reject.builder()
                .document(document)
                .reason(reason)
                .build();
    }
}
