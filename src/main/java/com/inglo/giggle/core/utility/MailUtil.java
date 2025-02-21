package com.inglo.giggle.core.utility;

import lombok.RequiredArgsConstructor;
import net.minidev.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    @Value("${spring.mail.username}")
    private String username;

    @Value("${spring.mail.password}")
    private String password;

    private static final String AUTHENTICATION_CODE_TEMPLATE = """
            <div style="width: 540px; border-top: 4px solid #02b875; border-bottom: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;">
            	<h1 style="margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;">
            		개발용 - <span style="color: #02b875;">인증코드</span> 안내입니다.
            	</h1>
            	<p style="font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;">
            		안녕하세요, 외국인 유학생을 위한 서비스 Giggle 입니다.<br />
            		요청하신 인증코드가 생성되었습니다.<br />
            		아래 인증코드로 회원가입을 진행해주세요.<br />
            		감사합니다.
            	</p>
            	<p style="font-size: 16px; margin: 40px 5px 20px; line-height: 28px;">
            		인증코드: <br />
            		<span style="font-size: 24px;">${AuthenticationCode}</span>
            	</p>
            </div>
            """;

    private static final String FORGET_PASSWORD_TEMPLATE = """
            <div style="width: 540px; border-top: 4px solid #02b875; border-bottom: 4px solid #02b875; margin: 100px auto; padding: 30px 0; box-sizing: border-box;">
            	<h1 style="margin: 0; padding: 0 5px; font-size: 28px; font-weight: 400;">
            		개발용 - <span style="color: #02b875;">임시 비밀번호</span> 안내입니다.
            	</h1>
            	<p style="font-size: 16px; line-height: 26px; margin-top: 50px; padding: 0 5px;">
            		안녕하세요, 외국인 유학생을 위한 서비스 Giggle 입니다.<br />
            		요청하신 임시 비밀번호가 생성되었습니다.<br />
            		아래 임시 비밀번호로 로그인 이후 비밀번호를 변경해주세요.<br />
            		감사합니다.
            	</p>
            	<p style="font-size: 16px; margin: 40px 5px 20px; line-height: 28px;">
            		임시 비밀번호: <br />
            		<span style="font-size: 24px;">${TemporaryPassword}</span>
            	</p>
            </div>
           \s""";

    private final JavaMailSender javaMailSender;

    public String getSendAuthenticationCodeRequestBody(
            String receiverAddress,
            String authenticationCode
    ) {

        JSONObject payload = new JSONObject();
        payload.put("subject", "Giggle 인증코드 안내");
        payload.put("text", AUTHENTICATION_CODE_TEMPLATE.replace("${AuthenticationCode}", authenticationCode));
        payload.put("email", receiverAddress);
        payload.put("username", username);
        payload.put("password", password);

        return payload.toJSONString();
    }

    public String getSendTemporaryPasswordRequestBody(
            String receiverAddress,
            String temporaryPassword
    ) {
        JSONObject payload = new JSONObject();
        payload.put("subject", "Giggle 임시 비밀번호 안내");
        payload.put("text", FORGET_PASSWORD_TEMPLATE.replace("${TemporaryPassword}", temporaryPassword));
        payload.put("email", receiverAddress);
        payload.put("username", username);
        payload.put("password", password);

        return payload.toJSONString();
    }
}
