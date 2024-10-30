package com.inglo.giggle.document.domain.service;

import com.inglo.giggle.document.domain.Document;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DocumentService {

    public Document updateUrls(Document document, String wordUrl, String hwpUrl) {
        document.updateWordUrl(wordUrl);
        document.updateHwpUrl(hwpUrl);
        return document;
    }

}
