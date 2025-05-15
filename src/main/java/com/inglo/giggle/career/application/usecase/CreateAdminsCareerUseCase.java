package com.inglo.giggle.career.application.usecase;

import com.inglo.giggle.career.application.dto.request.CreateAdminsCareerRequestDto;
import com.inglo.giggle.core.annotation.bean.UseCase;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@UseCase
public interface CreateAdminsCareerUseCase {

    void execute(
            List<MultipartFile> images,
            CreateAdminsCareerRequestDto requestDto
    );
}
