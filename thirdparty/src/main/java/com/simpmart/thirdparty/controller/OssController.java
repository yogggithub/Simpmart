package com.simpmart.thirdparty.controller;

import com.simpmart.common.utils.R;
import io.minio.MinioClient;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.xmlpull.v1.XmlPullParserException;

import javax.websocket.server.PathParam;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

@RestController
@RequestMapping("thirdparty/oss")
public class OssController {

    @Autowired
    private MinioClient minioClient;

    @Value("${spring.minio.bucket}")
    private String bucketName;

    /**
     * Get the pre-signed url
     *
     * @param objectName The name of the uploaded file in the OSS
     * @return
     */
    @RequestMapping("/url")
    public R getPresignedUrl(@PathParam("objectName") String objectName) {
        try {
            return R.ok()
                    // the pre-signed url will expire in 1 hour by default
                    .put("data", minioClient.presignedPutObject(bucketName, objectName, 3600));
        } catch (InvalidBucketNameException | NoSuchAlgorithmException |
                InsufficientDataException | IOException | InvalidKeyException |
                NoResponseException | XmlPullParserException | ErrorResponseException |
                InternalException | InvalidExpiresRangeException e) {
            return R.error("Error Occur: " + e);
        }
    }

    /**
     * a test controller
     * visit localhost:8501/oss/test/url by browser, will get a encrypted url
     *
     * @return
     */
    @RequestMapping("/test/url")
    public R getPresignedUrl() {
        try {
            return R.ok()
                    // the pre-signed url will expire in 1 hour by default
                    .put("data", minioClient.presignedPutObject(bucketName, "test", 3600));
        } catch (InvalidBucketNameException | NoSuchAlgorithmException |
                InsufficientDataException | IOException | InvalidKeyException |
                NoResponseException | XmlPullParserException | ErrorResponseException |
                InternalException | InvalidExpiresRangeException e) {
            return R.error("Error Occur: " + e);
        }
    }
}
