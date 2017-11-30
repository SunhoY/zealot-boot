package io.harry.zealotboot.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import io.harry.zealotboot.service.exception.ServiceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.InputStream;

import static io.harry.zealotboot.config.aws.s3.S3Constant.BUCKET_NAME;
import static io.harry.zealotboot.config.aws.s3.S3Constant.FILE_LENGTH;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest(RandomStringUtils.class)
public class AjaeGagStorageS3ServiceTest {
    @Mock
    private AmazonS3 mockAmazonS3;
    @Mock
    private InputStream mockInputStream;

    @Rule
    private ExpectedException expectedException = ExpectedException.none();

    private AjaeGagStorageS3Service subject;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RandomStringUtils.class);

        when(RandomStringUtils.randomAlphanumeric(FILE_LENGTH)).thenReturn("random_file_name");

        subject = new AjaeGagStorageS3Service(mockAmazonS3);
    }

    @Test
    public void uploadImage_putObjectWithBucketNameAndInputStream() throws Exception {
        subject.uploadImage(mockInputStream, "some string");

        verify(mockAmazonS3).putObject(
                eq(BUCKET_NAME), any(), eq(mockInputStream), eq(null));
    }

    @Test
    public void uploadImage_putObjectWithRandomFileName_generatedByUtil() throws Exception {
        subject.uploadImage(mockInputStream, "ajae_gag.jpg");

        verify(mockAmazonS3).putObject(
                anyString(), eq("random_file_name"), any(InputStream.class), eq(null));
    }

    @Test
    public void uploadImage_throwsException_whenErrorOccurred() throws Exception {
        when(mockAmazonS3.putObject(anyString(), any(), any(InputStream.class), eq(null)))
                .thenThrow(new SdkClientException("sdk client exception"));

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("sdk client exception");

        subject.uploadImage(mockInputStream, "some string");
    }
}