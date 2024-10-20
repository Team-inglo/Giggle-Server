package com.inglo.giggle.core.utility;

import com.amazonaws.services.s3.AmazonS3Client;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class ImageUtil {
    private final AmazonS3Client amazonS3Client;

    private final String IMAGE_CONTENT_PREFIX = "image/";

    @Value("${cloud.aws.s3.bucket}")
    private String bucketName;
    @Value("${cloud.aws.s3.url}")
    private String bucketUrl;

    @Getter
    @Value("${cloud.aws.s3.user-default-img-url}")
    private String userDefaultImgUrl;

    @Getter
    @Value("${cloud.aws.s3.owner-default-img-url}")
    private String ownerDefaultImgUrl;

    public String uploadUserProfileImageFile(MultipartFile file, String serialId) {
        final String contentType = file.getContentType();
        assert contentType != null;
        String type = "." + contentType.substring(contentType.indexOf("/") + 1);

        if (!contentType.startsWith(IMAGE_CONTENT_PREFIX)) {
            throw new CommonException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        }

        String uuid = UUID.randomUUID().toString();
        String fileName = "users/" + "account_" + serialId + "/" + "profile_img/" + uuid + type;

        try {
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), null);
        } catch (IOException e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
        return bucketUrl + fileName;
    }

    public String uploadOwnerIconImageFile(MultipartFile file, String serialId) {
        final String contentType = file.getContentType();
        assert contentType != null;
        String type = "." + contentType.substring(contentType.indexOf("/") + 1);

        if (!contentType.startsWith(IMAGE_CONTENT_PREFIX)) {
            throw new CommonException(ErrorCode.UNSUPPORTED_MEDIA_TYPE);
        }

        String uuid = UUID.randomUUID().toString();
        String fileName = "owners/" + "account_" + serialId + "/" + "icon_img/" + uuid + type;

        try {
            amazonS3Client.putObject(bucketName, fileName, file.getInputStream(), null);
        } catch (IOException e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
        return bucketUrl + fileName;
    }
}
