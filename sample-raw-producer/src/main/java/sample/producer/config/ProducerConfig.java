package sample.producer.config;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerializer;
import sample.producer.domain.WorkUnit;

import java.util.Properties;

@Configuration
public class ProducerConfig {

    @Bean
    public KafkaProducer<String, WorkUnit> workUnitProducer(KafkaProducerProperties kafkaProducerProperties) {
        Properties kafkaProps = new Properties();
        kafkaProps.put("bootstrap.servers", kafkaProducerProperties.getBootstrap());

//        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
//        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, WorkUnit> producer = new KafkaProducer<>(kafkaProps, stringKeySerializer(), workUnitJsonSerializer());
        return producer;
    }

    @Bean
    public Serializer stringKeySerializer() {
        return new StringSerializer();
    }

    @Bean
    public Serializer workUnitJsonSerializer() {
        return new JsonSerializer();
    }
}
