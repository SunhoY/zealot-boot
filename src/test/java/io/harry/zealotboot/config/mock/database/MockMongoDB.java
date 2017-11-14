package io.harry.zealotboot.config.mock.database;

import com.mongodb.MongoClient;
import cz.jirutka.spring.embedmongo.EmbeddedMongoFactoryBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.io.IOException;

@Profile("persist_test")
@Configuration
public class MockMongoDB {
    private static final String MONGO_DB_URL = "0.0.0.0";
    private static final String MONGO_DB_NAME = "zealot_test";

    @Bean
    public MongoTemplate mongoTemplate() throws IOException {
        EmbeddedMongoFactoryBean mongo = new EmbeddedMongoFactoryBean();

        mongo.setBindIp(MONGO_DB_URL);

        MongoClient mongoClient = mongo.getObject();
        MongoTemplate mongoTemplate = new MongoTemplate(mongoClient, MONGO_DB_NAME);

        return mongoTemplate;
    }
}
