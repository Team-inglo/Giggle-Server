package com.inglo.giggle.core.utility;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class MailUtil {

    @Value("${spring.mail.username}")
    private String senderAddress;

    private static final String AUTHENTICATION_CODE_TEMPLATE = """
            <!doctype html>
            <html lang="ko">
              <head>
                <meta charset="UTF-8" />
                <title>Giggle 인증코드 안내 / Verification Code</title>
              </head>
              <body style="margin:0; padding:64px; font-family:Arial,sans-serif; background-color:#ffffff;">
                <div style="width:100%; max-width:540px; margin:0 auto; border-top:4px solid #02b875; border-bottom:4px solid #02b875; padding:30px 0; box-sizing:border-box;">
            
                  <!-- 제목 (한/영) -->
                  <h1 style="margin:0; padding:0 5px; font-size:28px; font-weight:400; text-align:left;">
                    <span style="color:#02b875;">인증코드</span> 안내입니다. / <span style="color:#02b875;">Verification Code</span> Information
                  </h1>
            
                  <!-- 내용 (한글) -->
                  <p style="font-size:16px; line-height:26px; margin-top:50px; padding:0 5px; color:#333;">
                    안녕하세요, 국내 거주 외국인을 위한 서비스 <strong>Giggle</strong>입니다.<br />
                    요청하신 인증코드가 생성되었습니다.<br />
                    아래 인증코드를 입력하여 회원가입을 완료해주세요.<br />
                    감사합니다.
                  </p>
            
                  <!-- 내용 (영문) -->
                  <p style="font-size:16px; line-height:26px; margin-top:30px; padding:0 5px; color:#333;">
                    Hello, this is <strong>Giggle</strong>, a service for foreigners in Korea.<br />
                    Your requested verification code has been issued.<br />
                    Please enter the code below to complete your sign-up process.<br />
                    Thank you!
                  </p>
            
                  <!-- 인증코드 -->
                  <p style="font-size:16px; margin:40px 5px 20px; line-height:28px; color:#333;">
                    인증코드 / Verification Code: <br />
                    <span style="font-size:24px; font-weight:bold;">${AuthenticationCode}</span>
                  </p>
            
                </div>
              </body>
            </html>
            """;

    private static final String FORGET_PASSWORD_TEMPLATE = """
            <!doctype html>
            <html lang="ko">
              <head>
                <meta charset="UTF-8" />
                <title>Giggle 임시 비밀번호 안내 / Temporary Password</title>
              </head>
              <body style="margin:0; padding:64px; font-family:Arial,sans-serif; background-color:#ffffff;">
                <div style="width:100%; max-width:540px; margin:0 auto; border-top:4px solid #02b875; border-bottom:4px solid #02b875; padding:30px 0; box-sizing:border-box;">
            
                  <!-- 제목 -->
                  <h1 style="margin:0; padding:0 5px; font-size:28px; font-weight:400; text-align:left;">
                    <span style="color:#02b875;">임시 비밀번호</span> 안내입니다. / <span style="color:#02b875;">Temporary Password</span> Information
                  </h1>
            
                  <!-- 본문 (한글) -->
                  <p style="font-size:16px; line-height:26px; margin-top:50px; padding:0 5px; color:#333;">
                    안녕하세요, 국내 거주 외국인을 위한 서비스 <strong>Giggle</strong> 입니다.<br />
                    요청하신 임시 비밀번호가 생성되었습니다.<br />
                    아래의 임시 비밀번호로 로그인하신 후, 반드시 새로운 비밀번호로 변경해주세요.<br />
                    감사합니다.
                  </p>
            
                  <!-- 본문 (영어) -->
                  <p style="font-size:16px; line-height:26px; margin-top:30px; padding:0 5px; color:#333;">
                    Hello, this is <strong>Giggle</strong>, a service for foreigners in Korea.<br />
                    Your temporary password has been issued as requested.<br />
                    Please log in with the temporary password below and make sure to change it to a new one afterwards.<br />
                    Thank you!
                  </p>
            
                  <!-- 비밀번호 출력 -->
                  <p style="font-size:16px; margin:40px 5px 20px; line-height:28px; color:#333;">
                    임시 비밀번호 / Temporary Password: <br />
                    <span style="font-size:24px; font-weight:bold;">${TemporaryPassword}</span>
                  </p>
            
                </div>
              </body>
            </html>
            """;


    private final JavaMailSender javaMailSender;

    public void sendAuthenticationCode(
            String receiverAddress,
            String authenticationCode
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setSubject("Giggle에서 발급한 인증코드입니다. - [" + authenticationCode + "]");

        // 위 HTML을 이용하여 이메일을 작성하고 전송하는 코드
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(senderAddress);
        mimeMessageHelper.setTo(receiverAddress);
        // UTF-8로 인코딩
        mimeMessageHelper.setText(AUTHENTICATION_CODE_TEMPLATE.replace("${AuthenticationCode}", authenticationCode), true);

        javaMailSender.send(mimeMessage);
    }

    public void sendTemporaryPassword(
            String receiverAddress,
            String temporaryPassword
    ) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        mimeMessage.setSubject("Giggle에서 발급한 임시 비밀번호입니다.");

        // 위 HTML을 이용하여 이메일을 작성하고 전송하는 코드
        MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, true);
        mimeMessageHelper.setFrom(senderAddress);
        mimeMessageHelper.setTo(receiverAddress);

        // UTF-8로 인코딩
        mimeMessageHelper.setText(FORGET_PASSWORD_TEMPLATE.replace("${TemporaryPassword}", temporaryPassword), true);

        javaMailSender.send(mimeMessage);
    }
}
