package com.inglo.giggle.resume.application.service;

import com.inglo.giggle.account.domain.Owner;
import com.inglo.giggle.account.repository.OwnerRepository;
import com.inglo.giggle.resume.application.dto.response.UpdateOwnerResumeBookMarkResumeResponseDto;
import com.inglo.giggle.resume.application.usecase.UpdateOwnerResumeBookMarkResumeUseCase;
import com.inglo.giggle.resume.domain.BookMarkResume;
import com.inglo.giggle.resume.domain.Resume;
import com.inglo.giggle.resume.repository.BookMarkResumeRepository;
import com.inglo.giggle.resume.repository.ResumeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateOwnerResumeBookMarkResumeService implements UpdateOwnerResumeBookMarkResumeUseCase {

    private final OwnerRepository ownerRepository;
    private final ResumeRepository resumeRepository;
    private final BookMarkResumeRepository bookMarkResumeRepository;

    @Override
    @Transactional
    public UpdateOwnerResumeBookMarkResumeResponseDto execute(UUID ownerId, UUID resumeId) {

        Boolean isBookMarked = bookMarkResumeRepository.findByOwnerIdAndResumeId(ownerId, resumeId)
                .map(bookMarkResume -> {
                    bookMarkResumeRepository.delete(bookMarkResume);
                    return false;
                })
                .orElseGet(() -> {
                    Owner owner = ownerRepository.findByIdOrElseThrow(ownerId);
                    Resume resume = resumeRepository.findByIdOrElseThrow(resumeId);

                    BookMarkResume bookMarkResume = BookMarkResume.builder()
                            .owner(owner)
                            .resume(resume)
                            .build();

                    bookMarkResumeRepository.save(bookMarkResume);

                    return true;
                });

        return UpdateOwnerResumeBookMarkResumeResponseDto.builder()
                .isBookMarked(isBookMarked)
                .build();
    }
}
