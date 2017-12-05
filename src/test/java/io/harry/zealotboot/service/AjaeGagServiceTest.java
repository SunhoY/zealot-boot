package io.harry.zealotboot.service;

import io.harry.zealotboot.model.AjaeGag;
import io.harry.zealotboot.repository.AjaeGagRepository;
import org.joda.time.DateTimeUtils;
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
    private static final boolean DOES_NOT_MATTER = true;
    private AjaeGagService subject;
    private static final long MILLIS_20170619 = 1497830400;

    @Mock
    private AjaeGagRepository mockAjaeGagRepository;

    @Before
    public void setUp() throws Exception {
        DateTimeUtils.setCurrentMillisFixed(MILLIS_20170619);
        MockitoAnnotations.initMocks(this);

        when(mockAjaeGagRepository.findAllByVerifiedEqualsOrderByCreatedAtDesc(anyBoolean()))
                .thenReturn(Arrays.asList(new AjaeGag(), new AjaeGag()));

        subject = new AjaeGagService(mockAjaeGagRepository);
    }

    @After
    public void tearDown() throws Exception {
        reset(mockAjaeGagRepository);
    }

    @Test
    public void getAjaeGagList_callsFindAllByVerified_fromAjaeRepository() throws Exception {
        subject.getAjaeGagList(true);

        verify(mockAjaeGagRepository, times(1)).findAllByVerifiedEqualsOrderByCreatedAtDesc(true);
    }

    @Test
    public void getAjaeGagList_returnsObjectGotFromFindAll() throws Exception {
        assertThat(subject.getAjaeGagList(DOES_NOT_MATTER)).isEqualTo(Arrays.asList(new AjaeGag(), new AjaeGag()));
    }

    @Test
    public void createAjaeGag_storeAjaeGagInRepository_withSystemTimeAndFalseVerified() throws Exception {
        subject.createAjaeGag(new AjaeGag("some url"));

        AjaeGag expected = new AjaeGag("some url");
        expected.setCreatedAt(MILLIS_20170619);
        expected.setVerified(false);

        verify(mockAjaeGagRepository).save(expected);
    }

    @Test
    public void createAjaeGag_returnsCreatedAjaeGag() throws Exception {
        when(mockAjaeGagRepository.save(any(AjaeGag.class))).thenReturn(new AjaeGag("result"));

        assertThat(subject.createAjaeGag(new AjaeGag("some url"))).isEqualTo(new AjaeGag("result"));
    }
}