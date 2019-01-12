package com.book.mongo;

import com.mongodb.MongoClient;
import com.mongodb.MongoException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class MongoFactory {
    private static final Logger logger = LoggerFactory.getLogger(MongoFactory.class);

    private static final String HOST = "host";
    private static final String PORT_NUMBER = "portNumber";

    private static StoreServiceConfig storeServiceConfig;
    private static MongoClient mongoClient;

    public MongoFactory() {
        storeServiceConfig = new StoreServiceConfig();
    }

    public @Bean
    static MongoClient getMongo() {
        int port_no = Integer.parseInt(storeServiceConfig.getConfig().get(PORT_NUMBER).asText());
        String hostname = storeServiceConfig.getConfig().get(HOST).asText();
        if (mongoClient == null) {
            try {
                mongoClient = new MongoClient(hostname, port_no);
            } catch (MongoException ex) {
                logger.error(String.valueOf(ex));
            }
        }
        return mongoClient;
    }
}
