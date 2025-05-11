package com.inglo.giggle.admin.application.port.in.result;

import com.inglo.giggle.core.dto.SelfValidating;
import lombok.Getter;

@Getter
public class ReadAdminAccountOverviewResult extends SelfValidating<ReadAdminAccountOverviewResult> {
//TODO: 어케해야하노

//    @JsonProperty("accounts")
//    private final List<AccountOverviewDto> accounts;
//
//    @JsonProperty("page_info")
//    @NotNull(message = "페이지 정보를 입력해주세요.")
//    private final PageInfoDto pageInfo;
//
//    @Builder
//    public ReadAdminAccountOverviewResponseDto(List<AccountOverviewDto> accounts, PageInfoDto pageInfo) {
//        this.accounts = accounts;
//        this.pageInfo = pageInfo;
//        this.validateSelf();
//    }
//
//    public static class AccountOverviewDto extends SelfValidating<AccountOverviewDto> {
//        @JsonProperty("id")
//        @NotNull(message = "아이디는 필수입니다.")
//        private final UUID id;
//
//        @JsonProperty("email")
//        private final String email;
//
//        @JsonProperty("user_type")
//        @NotNull(message = "유저 타입은 필수입니다.")
//        private final ESecurityRole userType;
//
//        @JsonProperty("name")
//        @NotBlank(message = "이름은 필수입니다.")
//        private final String name;
//
//        @JsonProperty("birth")
//        private final String birth;
//
//        @JsonProperty("gender")
//        private final EGender gender;
//
//        @JsonProperty("nationality")
//        private final String nationality;
//
//        @JsonProperty("address")
//        private final String address;
//
//        @JsonProperty("visa")
//        private final EVisa visa;
//
//        @JsonProperty("phone_number")
//        private final String phoneNumber;
//
//        @JsonProperty("language")
//        private final ELanguage language;
//
//        @JsonProperty("sign_up_date")
//        private final String signUpDate;
//
//        @Builder
//        public AccountOverviewDto(UUID id, String email, ESecurityRole userType, String name, String birth, EGender gender, String nationality, String address, EVisa visa, String phoneNumber, ELanguage language, String signUpDate) {
//            this.id = id;
//            this.email = email;
//            this.userType = userType;
//            this.name = name;
//            this.birth = birth;
//            this.gender = gender;
//            this.nationality = nationality;
//            this.address = address;
//            this.visa = visa;
//            this.phoneNumber = phoneNumber;
//            this.language = language;
//            this.signUpDate = signUpDate;
//            this.validateSelf();
//        }
//
//        public static AccountOverviewDto from(
//                Account account
//        ) {
//            return AccountOverviewDto.builder()
//                    .id(account.getId())
//                    .email(account.getEmail())
//                    .userType(account.getRole())
//                    .name(account.getName())
//                    .birth(account instanceof User && ((User) account).getBirth() != null ? ((User) account).getBirth().toString() : null)
//                    .gender(account instanceof User ? ((User) account).getGender() : null)
//                    .nationality(account instanceof User ? ((User) account).getNationality() : null)
//                    .address(account instanceof User && ((User) account).getAddress() != null ? ((User) account).getAddress().getFullAddress() : null)
//                    .visa(account instanceof User ? ((User) account).getVisa() : null)
//                    .phoneNumber(account.getPhoneNumber())
//                    .language(account instanceof User ? ((User) account).getLanguage() : null)
//                    .signUpDate(account.getCreatedAt().toLocalDate().toString())
//                    .build();
//        }
//    }
//    public static ReadAdminAccountOverviewResponseDto of(List<Account> accounts, PageInfoDto pageInfo) {
//        return ReadAdminAccountOverviewResponseDto.builder()
//                .accounts(accounts.stream().map(AccountOverviewDto::from).toList())
//                .pageInfo(pageInfo)
//                .build();
//    }
}
