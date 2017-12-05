package io.harry.zealotboot.config;

import com.mongodb.MongoClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

@Profile("production")
@Configuration
public class MongoDB {
    private static final String DATABASE_NAME = "zealot";
    private static final String DATABASE_HOST = "localhost";

    @Bean
    public MongoClient mongoClient() {
        return new MongoClient(DATABASE_HOST);
    }

    @Bean
    @Autowired
    public MongoTemplate mongoTemplate(MongoClient mongoClient) {
        return new MongoTemplate(mongoClient, DATABASE_NAME);
    }
}
