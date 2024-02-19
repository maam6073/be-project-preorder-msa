package com.dohyeong.activity_service.global.s3.service;


import com.amazonaws.services.s3.AmazonS3;

import com.amazonaws.services.s3.model.CannedAccessControlList;
import com.amazonaws.services.s3.model.PutObjectRequest;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
@Service
public class S3Service {

    private final AmazonS3 amazonS3;

    @Value("${cloud.aws.s3.bucket}")
    private String bucket;

    private final String s3DirName = "images";

    public String imgUpload(MultipartFile multipartFile) throws IOException {
        File uploadFile = convert(multipartFile)
                .orElseThrow(() -> new IllegalArgumentException("MultipartFile -> File 전환 실패"));
        return imgUpload(uploadFile, s3DirName);
    }

    private String imgUpload(File imgUploadFile, String dirName) {
        String fileName = dirName + "/" + uuidRandomCreate(); // 파일명 uuid로 수정
        String uploadImageUrl = putS3(imgUploadFile, fileName);

        removeFile(imgUploadFile);  // 로컬에 생성된 File 삭제
        return uploadImageUrl;
    }

    public String imgUpdate(MultipartFile multipartFile, String imgName) throws IOException {
        String deleteFileName = s3DirName + "/" + imgName;
        if (!"".equals(s3DirName) && s3DirName != null) {
            boolean isExistImg = amazonS3.doesObjectExist(bucket, deleteFileName);
            if (isExistImg) {
                amazonS3.deleteObject(bucket, deleteFileName);
            }
        }

        return imgUpload(multipartFile);
    }

    private String putS3(File imgUploadFile, String fileName) {
        amazonS3.putObject(
                new PutObjectRequest(bucket, fileName, imgUploadFile)
                        .withCannedAcl(CannedAccessControlList.PublicRead)
        );
        return amazonS3.getUrl(bucket, fileName).toString();
    }

    private void removeFile(File targetFile) {
        if (targetFile.delete()) {
            log.info("파일이 삭제되었습니다.");
        } else {
            log.info("파일이 삭제되지 못했습니다.");
        }
    }

    private Optional<File> convert(MultipartFile file) throws IOException {
        File convertFile = new File(file.getOriginalFilename());
        if (convertFile.createNewFile()) {
            try (FileOutputStream fos = new FileOutputStream(convertFile)) {
                fos.write(file.getBytes());
            }
            return Optional.of(convertFile);
        }
        return Optional.empty();
    }

    private String uuidRandomCreate() {
        String resultUuid = "";
        try {
            UUID uuidTemp = UUID.randomUUID();
            resultUuid = uuidTemp.toString().replaceAll("-", "");
        } catch (Exception e) {
            log.info("uuidRandomCreate [error][e] ---> " + e);
            log.info("uuidRandomCreate [error][e.getMessage()] ---> " + e.getMessage());
        }
        return resultUuid;
    }
}