package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.request.UpdateAdminsCareerRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@UseCase
public interface UpdateAdminsCareerUseCase {

    void execute(
            Long careerId,
            List<MultipartFile> images,
            UpdateAdminsCareerRequestDto requestDto
    );
}
