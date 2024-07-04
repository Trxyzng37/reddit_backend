package com.trxyzng.trung.authentication.shared.beanConfigs;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.stereotype.Component;

@Component
public class MongoConfig {
    private final MongoDatabaseFactory mongo;

    public MongoConfig(MongoDatabaseFactory mongo) {
        this.mongo = mongo;
    }
}
