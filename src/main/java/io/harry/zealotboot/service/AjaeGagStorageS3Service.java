package io.harry.zealotboot.service;

import com.amazonaws.services.s3.AmazonS3;
import io.harry.zealotboot.service.exception.ServiceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;

import static io.harry.zealotboot.config.aws.s3.S3Constant.BUCKET_NAME;
import static io.harry.zealotboot.config.aws.s3.S3Constant.FILE_LENGTH;

@Service
public class AjaeGagStorageS3Service implements AjaeGagStorageService {
    private AmazonS3 amazonS3;

    @Autowired
    public AjaeGagStorageS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }

    @Override
    public void uploadImage(InputStream inputStream, String fileName) throws ServiceException {
        try {
            String randomeFileName = RandomStringUtils.randomAlphanumeric(FILE_LENGTH);

            this.amazonS3.putObject(BUCKET_NAME, randomeFileName, inputStream, null);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
