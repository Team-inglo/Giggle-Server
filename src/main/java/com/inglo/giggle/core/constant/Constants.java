package com.inglo.giggle.core.constant;

import java.util.List;

public class Constants {

    // JWT
    public static String ACCOUNT_ID_ATTRIBUTE_NAME = "ACCOUNT_ID";
    public static String ACCOUNT_ID_CLAIM_NAME = "aid";
    public static String ACCOUNT_ROLE_CLAIM_NAME = "rol";

    // HEADER
    public static String BEARER_PREFIX = "Bearer ";
    public static String AUTHORIZATION_HEADER = "Authorization";
    public static String KAKAO_AUTHORIZATION_HEADER = "KakaoAK ";

    // COOKIE
    public static String ACCESS_TOKEN = "access_token";
    public static String REFRESH_TOKEN = "refresh_token";

    // PartTimeEmploymentPermit
    public static String KEMPLOYEE_FULL_NAME = "kname";
    public static String KMAJOR = "kmj";
    public static String KTERM_OF_COMPLETION = "kterm";
    public static String KEMPLOYEE_PHONE_NUMBER = "전화번호";
    public static String KEMPLOYEE_EMAIL = "kemail";
    public static String KCOMPANY_NAME = "kcname";
    public static String KCOMPANY_REGISTRATION_NUMBER = "kcrnum";
    public static String KJOB_TYPE = "kjob";
    public static String KEMPLOYER_ADDRESS = "kaddr";
    public static String KEMPLOYER_NAME = "kon";
    public static String KEMPLOYER_PHONE_NUMBER = "kop";
    public static String KWORK_PERIOD = "kwp";
    public static String KHOURLY_RATE = "khr";
    public static String KWORK_DAYS_WEEKDAYS = "kwd";
    public static String KWORK_DAYS_WEEKENDS = "kwe";
    public static String SIGNATURE = "(인 또는 서명)";

    // StandardLaborContract
    public static final String EMPLOYER_NAME1 = "employername";
    public static final String EMPLOYEE_NAME1 = "employeename";
    public static final String START_YEAR = "startyear";
    public static final String START_MONTH = "startmonth";
    public static final String START_DAY = "startday";
    public static final String ADDRESS = "addr";
    public static final String DESCRIPTION = "description";

    public static final String DAY_PREFIX = "-day";
    public static final String START_HOUR_PREFIX = "-start-hour";
    public static final String START_MINUTE_PREFIX = "-start-minute";
    public static final String END_HOUR_PREFIX = "-end-hour";
    public static final String END_MINUTE_PREFIX = "-end-minute";
    public static final String TIME_PREFIX = "-time";
    public static final String START_REST_PREFIX = "-rest-start-hour";
    public static final String START_REST_MINUTE_PREFIX = "-rest-start-minute";
    public static final String END_REST_PREFIX = "-rest-end-hour";
    public static final String END_REST_MINUTE_PREFIX = "-rest-end-minute";

    public static final String REST_DAY = "restday";

    public static final String S_HOURLY_RATE = "hourlyrate";
    public static final String YES_BONUS = "yesbonus";
    public static final String BONUS = "bonus";
    public static final String NO_BONUS = "nobonus";

    public static final String YES_ADD = "yesadd";
    public static final String NO_ADD = "noadd";
    public static final String WAGE_RATE = "wagerate";
    public static final String PAYMENT_DAY = "paymentday";

    public static final String DIRECT_PAYMENT = "direct";
    public static final String BANK_PAYMENT = "bank";
    public static final String EMPLOYER_PHONE = "employerphone";
    public static final String EMPLOYER_NAME_2 = "employername2";
    public static final String COMPANY_NAME_1 = "companyname";
    public static final String ADDRESS_2 = "addr2";

    public static final String EMPLOYEE_PHONE = "employeephone";
    public static final String EMPLOYEE_NAME_2 = "employeename2";
    public static final String ADDRESS_3 = "addr3";

    public static final String EMPLOYMENT_INSURANCE = "고용";
    public static final String INDUSTRIAL_INSURANCE = "산재";
    public static final String NATIONAL_PENSION = "국민";
    public static final String HEALTH_INSURANCE = "건강";

    public static String CHECK = "■";
    public static String NONECHECK = "□";

    // Integrated Application
    public static String SURNAME = "surname";
    public static String GIVEN_NAME = "givenname";
    public static String BIRTHYEAR = "birthyear";
    public static String BIRTHMONTH = "birthmonth";
    public static String BIRTHDAY = "birthday";
    public static String MALE = "male";
    public static String FEMALE = "female";
    public static String NATIONALITY = "nationality";
    public static String ADDR = "addr";
    public static String TELENUM = "telenum";
    public static String CELLNUM = "cellnum";
    public static String SCHOOLNAME = "schoolname";
    public static String SCHOOLPHONE = "schoolphone";
    public static String KYESACC = "kyesacc";
    public static String YESACC = "yesacc";
    public static String KNOACC = "knoacc";
    public static String NOACC = "noacc";
    public static String WORKPLACE = "workplace";
    public static String REGNUM = "regnum";
    public static String WORKNUM = "worknum";
    public static String ANNUAL = "annual";
    public static String OCCUPATION = "occupation";
    public static String EMAIL = "email";
    public static String NAME = "name";
    public static String SIGNATURE1 = "${hand}";
    public static String SIGNATURE2 = "${hand2}";



    public static String BLANK = "";
    public static String V = "V";



    
    /**
     * 인증이 필요 없는 URL
     */
    public static List<String> NO_NEED_AUTH_URLS = List.of(
            // Authentication/Authorization
            "/v1/auth/validations/authentication-code",
            "/v1/auth/validations/email",
            "/v1/auth/validations/id",
            "/v1/auth/reissue/token",
            "/v1/auth/reissue/authentication-code",
            "/v1/auth/reissue/password",
            "/v1/auth/sign-up",
            "/v1/auth/login",
            "/v1/auth/users",
            "/v1/auth/owners",

            // Term
            "/v1/terms/**",

            // Guest
            "/v1/guests/**",

            // Swagger
            "/api-docs.html",
            "/api-docs/**",
            "/swagger-ui/**",
            "/v3/**",

            "/test-osrm"
    );

    /**
     * Swagger 에서 사용하는 URL
     */
    public static List<String> SWAGGER_URLS = List.of(
            "/api-docs.html",
            "/api-docs",
            "/swagger-ui",
            "/v3"
    );

    /**
     * 사용자 URL
     */
    public static List<String> USER_URLS = List.of(
            "/v1/**"
    );
}
