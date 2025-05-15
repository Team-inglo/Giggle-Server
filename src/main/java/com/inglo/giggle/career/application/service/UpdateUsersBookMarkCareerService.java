package com.inglo.giggle.career.application.service;

import com.inglo.giggle.account.domain.User;
import com.inglo.giggle.account.repository.UserRepository;
import com.inglo.giggle.career.application.dto.response.UpdateUsersBookMarkCareerResponseDto;
import com.inglo.giggle.career.application.usecase.UpdateUsersBookMarkCareerUseCase;
import com.inglo.giggle.career.domain.BookMarkCareer;
import com.inglo.giggle.career.domain.Career;
import com.inglo.giggle.career.repository.BookMarkCareerRepository;
import com.inglo.giggle.career.repository.CareerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UpdateUsersBookMarkCareerService implements UpdateUsersBookMarkCareerUseCase {

    private final BookMarkCareerRepository bookMarkCareerRepository;
    private final CareerRepository careerRepository;
    private final UserRepository userRepository;

    @Override
    @Transactional
    public UpdateUsersBookMarkCareerResponseDto execute(UUID accountId, Long careerId) {
        // 북마크 여부 확인 -> 북마크 생성 및 삭제
        boolean isBookMarked = bookMarkCareerRepository.existsByCareerIdAndUserId(careerId, accountId);

        if (isBookMarked) {
            bookMarkCareerRepository.deleteByCareerIdAndUserId(careerId, accountId);
        } else {
            User user = userRepository.findByIdOrElseThrow(accountId);
            Career career = careerRepository.findByIdOrElseThrow(careerId);

            BookMarkCareer bookMarkCareer = BookMarkCareer.builder()
                    .user(user)
                    .career(career)
                    .build();

            bookMarkCareerRepository.save(
                    bookMarkCareer
            );
        }

        return UpdateUsersBookMarkCareerResponseDto.of(!isBookMarked);
    }
}
