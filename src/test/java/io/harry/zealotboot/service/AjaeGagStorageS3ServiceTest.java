package io.harry.zealotboot.service;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.util.IOUtils;
import io.harry.zealotboot.service.exception.ServiceException;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

import static io.harry.zealotboot.config.aws.s3.S3Constant.BUCKET_NAME;
import static io.harry.zealotboot.config.aws.s3.S3Constant.FILE_LENGTH;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({RandomStringUtils.class, IOUtils.class})
public class AjaeGagStorageS3ServiceTest {
    private static final byte[] FAKE_BYTE_ARRAY = {1, 2};

    @Mock
    private AmazonS3 mockAmazonS3;
    @Mock
    private InputStream mockInputStream;

    @Captor
    private ArgumentCaptor<ByteArrayInputStream> byteArrayInputStreamCaptor;
    @Captor
    private ArgumentCaptor<ObjectMetadata> objectMetadataCaptor;

    @Rule
    private ExpectedException expectedException = ExpectedException.none();

    private AjaeGagStorageS3Service subject;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);
        PowerMockito.mockStatic(RandomStringUtils.class);
        PowerMockito.mockStatic(IOUtils.class);

        when(RandomStringUtils.randomAlphanumeric(FILE_LENGTH)).thenReturn("random_file_name");
        when(IOUtils.toByteArray(mockInputStream)).thenReturn(FAKE_BYTE_ARRAY);

        subject = new AjaeGagStorageS3Service(mockAmazonS3);
    }

    @Test
    public void uploadImage_transformsInputStreamToByteArrayInputStream() throws Exception {
        subject.uploadImage(mockInputStream);

        verify(mockAmazonS3).putObject(anyString(), anyString(),
                byteArrayInputStreamCaptor.capture(), any());

        assertThat(byteArrayInputStreamCaptor.getValue().read()).isEqualTo(1);
        assertThat(byteArrayInputStreamCaptor.getValue().read()).isEqualTo(2);
    }

    @Test
    public void uploadImage_putObjectWithRandomFileName_generatedByUtil() throws Exception {
        subject.uploadImage(mockInputStream);

        verify(mockAmazonS3).putObject(
                anyString(), eq("random_file_name"), any(InputStream.class), any(ObjectMetadata.class));
    }

    @Test
    public void uploadImage_setsContentLengthOfInputStream_asAnObjectMetadata() throws Exception {
        subject.uploadImage(mockInputStream);

        verify(mockAmazonS3).putObject(
                anyString(), anyString(), any(InputStream.class), objectMetadataCaptor.capture());

        assertThat(objectMetadataCaptor.getValue().getContentLength()).isEqualTo(FAKE_BYTE_ARRAY.length);
    }

    @Test
    public void uploadImage_putObjectWithBucketName() throws Exception {
        subject.uploadImage(mockInputStream);

        verify(mockAmazonS3).putObject(
                eq(BUCKET_NAME), any(), any(InputStream.class), any());
    }

    @Test
    public void uploadImage_throwsException_whenErrorOccurred() throws Exception {
        when(mockAmazonS3.putObject(anyString(), any(), any(InputStream.class), any()))
                .thenThrow(new SdkClientException("sdk client exception"));

        expectedException.expect(ServiceException.class);
        expectedException.expectMessage("sdk client exception");

        subject.uploadImage(mockInputStream);
    }
}