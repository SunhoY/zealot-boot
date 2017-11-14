package io.harry.zealotboot.service;

import io.harry.zealotboot.model.AjaeGag;
import io.harry.zealotboot.repository.AjaeGagRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AjaeGagServiceTest {
    private AjaeGagService subject;

    private List<AjaeGag> ajaeGagList;

    @Mock
    AjaeGagRepository mockAjaeGagRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mockAjaeGagRepository.findAll()).thenReturn(Arrays.asList(new AjaeGag(), new AjaeGag()));

        subject = new AjaeGagService(mockAjaeGagRepository);

        ajaeGagList = subject.getAjaeGagList();
    }

    @After
    public void tearDown() throws Exception {
        reset(mockAjaeGagRepository);
    }

    @Test
    public void getAjaeGagList_callsAjaeGagRepository_getAjaeGagList() throws Exception {
        verify(mockAjaeGagRepository, times(1)).findAll();
    }

    @Test
    public void getAjaeGagList_returnsObjectGotFromFindAll() throws Exception {
        assertThat(ajaeGagList).isEqualTo(Arrays.asList(new AjaeGag(), new AjaeGag()));
    }
}