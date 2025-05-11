package com.inglo.giggle.document.application.port.out;

import com.inglo.giggle.document.domain.Document;

public interface LoadDocumentPort {

    Document loadDocument(Long id);
}
