package io.harry.zealotboot.repository;

import io.harry.zealotboot.model.AjaeGag;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@ActiveProfiles("persist_test")
@RunWith(SpringRunner.class)
@SpringBootTest
public class AjaeGagRepositoryTest {
    @Autowired
    private MongoTemplate mongoTemplate;

    @Autowired
    private AjaeGagRepository ajaeGagRepository;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.save(new AjaeGag("http://somewhere", 4444, false));
        mongoTemplate.save(new AjaeGag("http://overtherainbow", 5555, true));
        mongoTemplate.save(new AjaeGag("http://overtherainbow", 6666, true));
    }

    @After
    public void tearDown() throws Exception {
        mongoTemplate.dropCollection("ajae_gag");
    }

    @Test
    public void findAll() throws Exception {
        List<String> urlList = ajaeGagRepository.findAll().stream()
                .map(AjaeGag::getUrl)
                .collect(Collectors.toList());

        assertThat(urlList.size()).isEqualTo(3);
        assertThat(urlList.contains("http://somewhere")).isTrue();
        assertThat(urlList.contains("http://overtherainbow")).isTrue();
    }

    @Test
    public void findAllByVerifiedEqualsOrderByCreatedAtDesc() throws Exception {
        List<Long> createdAtList = ajaeGagRepository
                .findAllByVerifiedEqualsOrderByCreatedAtDesc(true)
                .stream().map(AjaeGag::getCreatedAt).collect(Collectors.toList());

        assertThat(createdAtList.get(0)).isEqualTo(6666);
        assertThat(createdAtList.get(1)).isEqualTo(5555);
    }
}