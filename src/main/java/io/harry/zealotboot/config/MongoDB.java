package io.harry.zealotboot.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.core.MongoTemplate;

@Configuration
public class MongoDB {

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient("localhost");
    }

    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, "zealot");
    }
}
