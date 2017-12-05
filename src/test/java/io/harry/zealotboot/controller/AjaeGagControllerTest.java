package io.harry.zealotboot.controller;

import io.harry.zealotboot.model.AjaeGag;
import io.harry.zealotboot.service.AjaeGagService;
import io.harry.zealotboot.service.AjaeGagStorageService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Arrays;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("controller_test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AjaeGagControllerTest {
    private final static String FAKE_AJAE_GAG_ID = "some id";
    private final static String FAKE_URL_STRING = "some url";

    private ResultActions result;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AjaeGagService mockAjaeGagService;

    @Autowired
    AjaeGagStorageService mockAjaeStorageService;

    @Mock
    private URL mockUrl;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mockUrl.toString()).thenReturn(FAKE_URL_STRING);

        when(mockAjaeGagService.getAjaeGagList()).thenReturn(Arrays.asList(new AjaeGag(), new AjaeGag()));
        when(mockAjaeStorageService.uploadImage(any(InputStream.class)))
                .thenReturn(mockUrl);
    }

    @After
    public void tearDown() throws Exception {
        reset(mockAjaeGagService);
        reset(mockAjaeStorageService);
    }

    @Test
    public void getAjaeGags_callsAjaeService_toFetchAllAjaeGags() throws Exception {
        result = mockMvc.perform(get("/ajae-gags"));

        verify(mockAjaeGagService, times(1)).getAjaeGagList();
    }

    @Test
    public void getAjaeGags_returnsListOfAjaeGags() throws Exception {
        result = mockMvc.perform(get("/ajae-gags"));

        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }

    @Test
    public void postAjaeGag_callsUploadImageService() throws Exception {
        MockMultipartFile mockMultipartFile = createMockMultipartFile();

        result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/ajae-gags")
                .file(mockMultipartFile))
                .andExpect(status().isOk());

        verify(mockAjaeStorageService).uploadImage(any(InputStream.class));
    }

    @Test
    public void postAjaeGag_callsCreateAjaeGagService() throws Exception {
        MockMultipartFile mockMultipartFile = createMockMultipartFile();

        result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/ajae-gags")
                .file(mockMultipartFile))
                .andExpect(status().isOk());

        verify(mockAjaeGagService).createAjaeGag(new AjaeGag(FAKE_URL_STRING));
    }

    @Test
    public void postAjaeGag_returnsWithResultedAjaeGagFromCreateService() throws Exception {
        MockMultipartFile mockMultipartFile = createMockMultipartFile();

        AjaeGag mockAjaeGage = mock(AjaeGag.class);
        when(mockAjaeGage.getId()).thenReturn(FAKE_AJAE_GAG_ID);
        when(mockAjaeGage.getUrl()).thenReturn(FAKE_URL_STRING);
        when(mockAjaeGagService.createAjaeGag(any(AjaeGag.class))).thenReturn(mockAjaeGage);

        result = mockMvc.perform(MockMvcRequestBuilders.fileUpload("/ajae-gags")
                .file(mockMultipartFile))
                .andExpect(jsonPath("$.data.id").value(FAKE_AJAE_GAG_ID))
                .andExpect(jsonPath("$.data.url").value(FAKE_URL_STRING))
                .andExpect(status().isOk());
    }

    private MockMultipartFile createMockMultipartFile() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("mockFile1.jpg").getFile());

        FileInputStream stream = new FileInputStream(file);

        return new MockMultipartFile("file", file.getName(), "multipart/form-data", stream);
    }
}