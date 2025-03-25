package com.inglo.giggle.document.repository.impl;

import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.document.domain.Document;
import com.inglo.giggle.document.repository.DocumentRepository;
import com.inglo.giggle.document.repository.mysql.DocumentJpaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class DocumentRepositoryImpl implements DocumentRepository {

    private final DocumentJpaRepository documentJpaRepository;

    @Override
    public Document findByIdOrElseNull(Long id) {
        return documentJpaRepository.findById(id).orElse(null);
    }

    @Override
    public Document findByIdOrElseThrow(Long id) {
        return documentJpaRepository.findById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DOCUMENT));
    }

    @Override
    public Document findWithUserOwnerJobPostingByIdOrElseThrow(Long id) {
        return documentJpaRepository.findWithUserOwnerJobPostingById(id)
                .orElseThrow(() -> new CommonException(ErrorCode.NOT_FOUND_DOCUMENT));
    }

    @Override
    public void save(Document document) {
        documentJpaRepository.save(document);
    }

    @Override
    public void delete(Document document) {
        documentJpaRepository.delete(document);
    }

    @Override
    public void deleteById(Long id) {
        documentJpaRepository.deleteById(id);
    }

}
