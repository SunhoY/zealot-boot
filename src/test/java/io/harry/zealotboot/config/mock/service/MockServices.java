package io.harry.zealotboot.config.mock.service;

import io.harry.zealotboot.service.AjaeGagService;
import io.harry.zealotboot.service.AjaeGagStorageService;
import org.mockito.Mockito;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;

@Profile("controller_test")
@Configuration
public class MockServices {
    @Bean
    @Primary
    public AjaeGagService ajaeService() {
        return Mockito.mock(AjaeGagService.class);
    }

    @Bean
    @Primary
    public AjaeGagStorageService ajaeGagStorageService() {
        return Mockito.mock(AjaeGagStorageService.class);
    }
}
