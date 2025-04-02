package com.inglo.giggle.document.persistence.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.persistence.entity.DocumentEntity;
import com.inglo.giggle.document.persistence.mapper.DocumentMapper;
import com.inglo.giggle.document.persistence.repository.DocumentRepository;
import com.inglo.giggle.document.persistence.repository.mysql.DocumentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepository {

    private final DocumentJpaRepository documentJpaRepository;

    @Override
    public Document findByIdOrElseThrow(Long id) {
        return DocumentMapper.toDomain(documentJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DOCUMENT)));
    }

    @Override
    public Document save(Document document) {
        DocumentEntity entity = documentJpaRepository.save(DocumentMapper.toEntity(document));
        return DocumentMapper.toDomain(entity);
    }

}
