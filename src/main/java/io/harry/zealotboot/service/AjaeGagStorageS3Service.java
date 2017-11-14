package io.harry.zealotboot.service;

import com.amazonaws.services.s3.AmazonS3;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AjaeGagStorageS3Service implements AjaeGagStorageService {
    private AmazonS3 amazonS3;

    @Autowired
    public AjaeGagStorageS3Service(AmazonS3 amazonS3) {
        this.amazonS3 = amazonS3;
    }
}
