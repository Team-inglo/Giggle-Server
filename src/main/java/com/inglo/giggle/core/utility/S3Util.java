package com.inglo.giggle.core.utility;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3Client;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import com.amazonaws.services.s3.model.PutObjectResult;
import com.inglo.giggle.core.exception.error.ErrorCode;
import com.inglo.giggle.core.exception.type.CommonException;
import com.inglo.giggle.core.type.EImageType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.UUID;

@Configuration
@RequiredArgsConstructor
public class S3Util {
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

    public String uploadImageFile(MultipartFile file, String serialId, EImageType eImageType) {
        try {
            String fileName = UUID.randomUUID().toString();
            ObjectMetadata objectMetadata = new ObjectMetadata();
            objectMetadata.setContentLength(file.getSize());
            objectMetadata.setContentType(file.getContentType());

            String key = switch (eImageType) {
                case USER_PROFILE_IMG -> "users/" + "account_" + serialId + "/" + "profile_img/" + fileName;
                case OWNER_PROFILE_IMG -> "owners/" + "account_" + serialId + "/" + "icon_img/" + fileName;
                case COMPANY_IMG -> "companies/" + "account_" + serialId + "/" + "company_img/" + fileName;
            };

            PutObjectRequest putObjectRequest = new PutObjectRequest(bucketName, key, file.getInputStream(), objectMetadata);

            amazonS3Client.putObject(putObjectRequest);

            return bucketUrl + key;
        } catch (SdkClientException | IOException e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
    }

    public String uploadWordFile(InputStream inputStream, String type, Long jobPostingId, String jobPostingTitle, String ownerName, String userName) {
        String uuid = UUID.randomUUID().toString();
        String fileName = "documents/" + jobPostingTitle + ":id_" + jobPostingId + "/" + type + "-" + ownerName + "_" + userName + "-" + uuid + ".docx";

        try {
            amazonS3Client.putObject(bucketName, fileName, inputStream, null);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
        return bucketUrl + fileName;
    }

    public String uploadHwpFile(InputStream inputStream, String type, Long jobPostingId, String jobPostingTitle, String ownerName, String userName) {
        String uuid = UUID.randomUUID().toString();
        String fileName = "documents/" + jobPostingTitle + ":id_" + jobPostingId + "/" + type + "-" + ownerName + "_" + userName + "-" + uuid + ".hwp";

        try {
            amazonS3Client.putObject(bucketName, fileName, inputStream, null);
        } catch (Exception e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
        return bucketUrl + fileName;
    }

    public void deleteFile(String fileUrl, EImageType eImageType, String serialId) {
        try {
            String fileName = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
            switch (eImageType) {
                case USER_PROFILE_IMG -> amazonS3Client.deleteObject(bucketName, "users/" + "account_" + serialId + "/" + "profile_img/" + fileName);
                case OWNER_PROFILE_IMG -> amazonS3Client.deleteObject(bucketName, "owners/" + "account_" + serialId + "/" + "icon_img/" + fileName);
                case COMPANY_IMG -> amazonS3Client.deleteObject(bucketName, "companies/" + "account_" + serialId + "/" + "company_img/" + fileName);
            }
        } catch (SdkClientException e) {
            throw new CommonException(ErrorCode.UPLOAD_FILE_ERROR);
        }
    }
}
