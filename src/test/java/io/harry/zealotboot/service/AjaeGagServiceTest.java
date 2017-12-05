package io.harry.zealotboot.service;

import io.harry.zealotboot.model.AjaeGag;
import io.harry.zealotboot.repository.AjaeGagRepository;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class AjaeGagServiceTest {
    private AjaeGagService subject;

    @Mock
    private AjaeGagRepository mockAjaeGagRepository;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        when(mockAjaeGagRepository.findAll()).thenReturn(Arrays.asList(new AjaeGag(), new AjaeGag()));

        subject = new AjaeGagService(mockAjaeGagRepository);
    }

    @After
    public void tearDown() throws Exception {
        reset(mockAjaeGagRepository);
    }

    @Test
    public void getAjaeGagList_callsAjaeGagRepository_getAjaeGagList() throws Exception {
        subject.getAjaeGagList();

        verify(mockAjaeGagRepository, times(1)).findAll();
    }

    @Test
    public void getAjaeGagList_returnsObjectGotFromFindAll() throws Exception {
        assertThat(subject.getAjaeGagList()).isEqualTo(Arrays.asList(new AjaeGag(), new AjaeGag()));
    }

    @Test
    public void createAjaeGag_storeAjaeGagInRepository() throws Exception {
        subject.createAjaeGag(new AjaeGag("some url"));

        verify(mockAjaeGagRepository).save(new AjaeGag("some url"));
    }

    @Test
    public void createAjaeGag_returnsCreatedAjaeGag() throws Exception {
        when(mockAjaeGagRepository.save(any(AjaeGag.class))).thenReturn(new AjaeGag("result"));

        assertThat(subject.createAjaeGag(new AjaeGag("some url"))).isEqualTo(new AjaeGag("result"));
    }
}