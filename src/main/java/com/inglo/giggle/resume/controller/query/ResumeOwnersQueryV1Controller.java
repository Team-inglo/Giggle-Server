package com.inglo.giggle.resume.controller.query;

import com.inglo.giggle.core.annotation.security.AccountID;
import com.inglo.giggle.core.dto.ResponseDto;
import com.inglo.giggle.core.type.EMajor;
import com.inglo.giggle.core.type.ENationality;
import com.inglo.giggle.core.type.EVisa;
import com.inglo.giggle.posting.domain.type.EJobCategory;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDtoV1;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeDetailResponseDtoV2;
import com.inglo.giggle.resume.application.dto.response.ReadOwnerResumeOverviewResponseDto;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeDetailUseCase;
import com.inglo.giggle.resume.application.usecase.ReadOwnerResumeOverviewUseCase;
import com.inglo.giggle.resume.domain.type.EKorean;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v1/owners")
public class ResumeOwnersQueryV1Controller {
    private final ReadOwnerResumeDetailUseCase readOwnerResumeDetailUseCase;
    private final ReadOwnerResumeOverviewUseCase readOwnerResumeOverviewUseCase;

    /**
     * 7.19 (고용주) 이력서 조회하기
     */
    @GetMapping("/user-owner-job-postings/{id}/users/resumes/details")
    public ResponseDto<ReadOwnerResumeDetailResponseDtoV1> readOwnerResumeDetail(
            @AccountID UUID accountId,
            @PathVariable Long id
    ) {
        return ResponseDto.ok(readOwnerResumeDetailUseCase.execute(accountId, id));
    }

    /**
     * 7.24 (고용주) 이력서 리스트 조회하기
     */
    @GetMapping("/resumes/overviews")
    public ResponseDto<ReadOwnerResumeOverviewResponseDto> readOwnerResumeOverviews(
            @AccountID UUID accountId,
            @RequestParam(value = "page") Integer page,
            @RequestParam(value = "size") Integer size,
            @RequestParam(value = "sorting", required = false) String sorting,
            @RequestParam(value = "visa", required = false) List<EVisa> visa,
            @RequestParam(value = "korean", required = false) List<EKorean> korean,
            @RequestParam(value = "major", required = false) List<EMajor> major,
            @RequestParam(value = "nationality", required = false) List<ENationality> nationality,
            @RequestParam(value = "industry", required = false) List<EJobCategory> industry
    ) {
        return ResponseDto.ok(readOwnerResumeOverviewUseCase.execute(
                accountId,
                page,
                size,
                sorting,
                visa,
                korean,
                major,
                nationality,
                industry
        ));
    }

    /**
     * 7.25 (고용주) 이력서 상세 조회하기
     */
    @GetMapping("/resumes/{id}/details")
    public ResponseDto<ReadOwnerResumeDetailResponseDtoV2> readOwnerResumeDetailById(
            @PathVariable UUID id
    ) {
        return ResponseDto.ok(readOwnerResumeDetailUseCase.execute(id));
    }
}
