package io.harry.zealotboot.service;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import io.harry.zealotboot.service.exception.ServiceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.net.URL;

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
    public URL uploadImage(InputStream inputStream) throws ServiceException {
        try {
            String randomFileName = RandomStringUtils.randomAlphanumeric(FILE_LENGTH);
            ObjectMetadata metadata = new ObjectMetadata();
            byte[] byteArray = IOUtils.toByteArray(inputStream);
            metadata.setContentLength(byteArray.length);
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(byteArray);

            this.amazonS3.putObject(BUCKET_NAME, randomFileName, byteArrayInputStream, metadata);

            return this.amazonS3.getUrl(BUCKET_NAME, randomFileName);
        } catch (Exception e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
