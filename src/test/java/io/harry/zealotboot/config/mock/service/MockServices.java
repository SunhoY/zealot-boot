package io.harry.zealotboot.config.mock.service;

import io.harry.zealotboot.service.AjaeService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("test")
@Configuration
public class MockServices {
    @Bean
    @Primary
    public AjaeService ajaeService() {
        return Mockito.mock(AjaeService.class);
    }
}
