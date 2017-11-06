package io.harry.zealotboot.controller;

import io.harry.zealotboot.model.AjaeGag;
import io.harry.zealotboot.service.AjaeService;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.Arrays;

import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class AjaeGagControllerTest {
    private ResultActions result;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    AjaeService ajaeService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(ajaeService.getAjaeGagList()).thenReturn(Arrays.asList(new AjaeGag(), new AjaeGag()));

        result = mockMvc.perform(get("/ajae-gags"));
    }

    @After
    public void tearDown() throws Exception {
        Mockito.reset(ajaeService);
    }

    @Test
    public void getAjaeGags_callsAjaeService_toFetchAllAjaeGags() throws Exception {
        verify(ajaeService).getAjaeGagList();
    }

    @Test
    public void getAjaeGags_returnsListOfAjaeGags() throws Exception {
        result.andExpect(status().isOk())
                .andExpect(jsonPath("$.data").isArray())
                .andExpect(jsonPath("$.data.length()").value(2));
    }
}