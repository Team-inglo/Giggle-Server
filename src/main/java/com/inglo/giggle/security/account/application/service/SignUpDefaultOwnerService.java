package com.inglo.giggle.security.account.application.service;

import com.inglo.giggle.core.domain.Address;
import com.inglo.giggle.core.dto.DefaultJsonWebTokenDto;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import com.inglo.giggle.core.utility.JsonWebTokenUtil;
import com.inglo.giggle.core.utility.S3Util;
import com.inglo.giggle.owner.application.port.in.command.CreateOwnerCommand;
import com.inglo.giggle.owner.application.port.in.usecase.CreateOwnerUseCase;
import com.inglo.giggle.security.account.application.port.in.command.SignUpDefaultOwnerCommand;
import com.inglo.giggle.security.account.application.port.in.result.SignUpDefaultOwnerResult;
import com.inglo.giggle.security.account.application.port.in.usecase.SignUpDefaultOwnerUseCase;
import com.inglo.giggle.security.account.application.port.out.CreateAccountPort;
import com.inglo.giggle.security.account.domain.Account;
import com.inglo.giggle.security.account.domain.type.ESecurityProvider;
import com.inglo.giggle.security.account.domain.type.ESecurityRole;
import com.inglo.giggle.security.temporaryaccount.application.port.in.command.DeleteTemporaryAccountCommand;
import com.inglo.giggle.security.temporaryaccount.application.port.in.query.ReadTemporaryAccountQuery;
import com.inglo.giggle.security.temporaryaccount.application.port.in.result.ReadTemporaryAccountResult;
import com.inglo.giggle.security.temporaryaccount.application.port.in.usecase.DeleteTemporaryAccountUseCase;
import com.inglo.giggle.security.temporarytoken.application.port.in.command.DeleteTemporaryTokenCommand;
import com.inglo.giggle.security.temporarytoken.application.port.in.query.ReadTemporaryTokenQuery;
import com.inglo.giggle.security.temporarytoken.application.port.in.result.ReadTemporaryTokenResult;
import com.inglo.giggle.security.temporarytoken.application.port.in.usecase.DeleteTemporaryTokenUseCase;
import com.inglo.giggle.termaccount.application.port.in.command.CreateTermAccountCommand;
import com.inglo.giggle.termaccount.application.port.in.usecase.CreateTermAccountUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class SignUpDefaultOwnerService implements SignUpDefaultOwnerUseCase {

    private final CreateAccountPort createAccountPort;

    private final CreateOwnerUseCase createOwnerUseCase;
    private final CreateTermAccountUseCase createTermAccountUseCase;
    private final DeleteTemporaryTokenUseCase deleteTemporaryTokenUseCase;
    private final DeleteTemporaryAccountUseCase deleteTemporaryAccountUseCase;
    private final ReadTemporaryTokenQuery readTemporaryTokenQuery;
    private final ReadTemporaryAccountQuery readTemporaryAccountQuery;

    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final S3Util s3Util;
    private final JsonWebTokenUtil jsonWebTokenUtil;

    @Override
    @Transactional
    public SignUpDefaultOwnerResult execute(SignUpDefaultOwnerCommand command) {

        // temporary Token 검증. Redis 에 있는 토큰인지 확인
        ReadTemporaryTokenResult readTemporaryTokenResult = readTemporaryTokenQuery.execute(command.getTemporaryToken());

        // Redis에서 임시 사용자 정보 가져오기
        ReadTemporaryAccountResult readTemporaryAccountResult = readTemporaryAccountQuery.execute(readTemporaryTokenResult.getEmail());

        // AccountType 검증
        validateAccountTypeOwner(readTemporaryAccountResult.getAccountType());

        // 아이콘 이미지 저장
        String iconUrl = s3Util.getOwnerDefaultImgUrl();
        if (command.getFile() != null) {
            iconUrl = s3Util.uploadImageFile(command.getFile(), readTemporaryAccountResult.getEmail(), EImageType.OWNER_PROFILE_IMG);
        }

        // Address 생성
        Address address;

        if (command.getAddress().addressName() == null || command.getAddress().addressName().isEmpty()) {
            address = null;
        } else {
            address = Address.builder()
                    .addressName(command.getAddress().addressName())
                    .addressDetail(command.getAddress().addressDetail())
                    .region1DepthName(command.getAddress().region1DepthName())
                    .region2DepthName(command.getAddress().region2DepthName())
                    .region3DepthName(command.getAddress().region3DepthName())
                    .region4DepthName(command.getAddress().region4DepthName())
                    .latitude(command.getAddress().latitude())
                    .longitude(command.getAddress().longitude())
                    .build();
        }

        // Account 생성 및 저장
        Account account = Account.builder()
                .provider(ESecurityProvider.DEFAULT)
                .role(ESecurityRole.OWNER)
                .serialId(readTemporaryAccountResult.getEmail())
                .password(bCryptPasswordEncoder.encode(readTemporaryAccountResult.getPassword()))
                .createdAt(LocalDateTime.now())
                .build();
        account = createAccountPort.createAccount(account);

        // Owner 생성 및 저장
        CreateOwnerCommand createOwnerCommand = new CreateOwnerCommand(
                readTemporaryAccountResult.getEmail(),
                iconUrl,
                command.getPhoneNumber(),
                command.getCompanyName(),
                command.getOwnerName(),
                command.getCompanyRegistrationNumber(),
                command.getMarketingAllowed(),
                command.getNotificationAllowed(),
                address
        );
        createOwnerUseCase.execute(createOwnerCommand);

        // temporary Token 삭제
        DeleteTemporaryTokenCommand deleteTemporaryTokenCommand = new DeleteTemporaryTokenCommand(readTemporaryTokenResult.getEmail());
        deleteTemporaryTokenUseCase.execute(deleteTemporaryTokenCommand);

        // temporaryAccount 삭제
        DeleteTemporaryAccountCommand deleteTemporaryAccountCommand = new DeleteTemporaryAccountCommand(
                readTemporaryAccountResult.getEmail()
        );
        deleteTemporaryAccountUseCase.execute(deleteTemporaryAccountCommand);

        // 약관 동의 생성
        CreateTermAccountCommand createTermAccountCommand = new CreateTermAccountCommand(
                account.getId(),
                command.getTermTypes()
        );
        createTermAccountUseCase.execute(createTermAccountCommand);

        // JWT 생성
        DefaultJsonWebTokenDto jsonWebTokenDto = jsonWebTokenUtil.generateDefaultJsonWebTokens(account.getId(), account.getRole());

        return new SignUpDefaultOwnerResult(
                jsonWebTokenDto.getAccessToken(),
                jsonWebTokenDto.getRefreshToken()
        );
    }

    private void validateAccountTypeOwner(ESecurityRole accountType) {
        if (!accountType.equals(ESecurityRole.OWNER)) {
            throw new CommonException(ErrorCode.INVALID_ACCOUNT_TYPE);
        }
    }
}
