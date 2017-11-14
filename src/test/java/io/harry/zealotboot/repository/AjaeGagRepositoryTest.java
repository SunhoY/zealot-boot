package io.harry.zealotboot.repository;

import io.harry.zealotboot.model.AjaeGag;
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
    MongoTemplate mongoTemplate;

    @Autowired
    AjaeGagRepository ajaeGagRepository;

    @Before
    public void setUp() throws Exception {
        mongoTemplate.save(new AjaeGag("http://somewhere"));
        mongoTemplate.save(new AjaeGag("http://overtherainbow"));
    }

    @Test
    public void findAll() throws Exception {
        List<String> urlList = ajaeGagRepository.findAll().stream()
                .map(ajaeGag -> ajaeGag.getUrl())
                .collect(Collectors.toList());

        assertThat(urlList.size()).isEqualTo(2);
        assertThat(urlList.contains("http://somewhere")).isTrue();
        assertThat(urlList.contains("http://overtherainbow")).isTrue();
    }
}