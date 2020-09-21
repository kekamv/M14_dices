package dices.configuration;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoClientURI;
import com.mongodb.WriteConcern;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.client.RestTemplate;

;


@Configuration
@EnableScheduling
@EnableAsync
public class
SpringConfiguration {


    @Bean
    public static PropertySourcesPlaceholderConfigurer propertyConfigInDev() {
        return new PropertySourcesPlaceholderConfigurer();
    }

    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory ret = new SimpleClientHttpRequestFactory();
        ret.setReadTimeout(10000); // 10 sec
        return ret;
    }

    @Bean
    public RestTemplate getRestTemplate(ClientHttpRequestFactory clientHttpRequestFactory) {
        return new RestTemplate(clientHttpRequestFactory);
    }

    @Value("${spring.mongodb.uri}")
    private String connectionString;


    public @Bean
    SimpleMongoDbFactory mongoDbFactory() {
        return new SimpleMongoDbFactory(new MongoClientURI(connectionString));
    }

    /*
    public @Bean
    MongoTemplate mongoTemplate() {
        return new MongoTemplate(mongoDbFactory());
    }

     */

    public @Bean
    MongoClient mongoClient(@Value("${spring.mongodb.uri}") String connectionString) {

        ConnectionString connString = new ConnectionString(connectionString);

        MongoClientSettings mongoClientSettings = MongoClientSettings
                .builder()
                .applyConnectionString(connString)
                .writeConcern(WriteConcern.MAJORITY)
                .build();

        MongoClient mongoClient = MongoClients.create(mongoClientSettings);

        return mongoClient;
    }



}
