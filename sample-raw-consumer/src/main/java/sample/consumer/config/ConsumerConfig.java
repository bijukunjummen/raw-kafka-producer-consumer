package sample.consumer.config;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import sample.consumer.domain.WorkUnit;
import sample.serializer.JsonDeserializer;

import java.util.Collections;
import java.util.Properties;

@Configuration
public class ConsumerConfig {

    @Bean
    public KafkaConsumer<String, WorkUnit> workUnitConsumer(KafkaConsumerProperties kafkaConsumerProperties) {
        Properties props = new Properties();
        props.put("bootstrap.servers", kafkaConsumerProperties.getBootstrap());
        props.put("group.id", kafkaConsumerProperties.getGroup());
        props.put("auto.offset.reset", "earliest");
//        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
//        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String, WorkUnit> consumer = new KafkaConsumer<>(props, stringKeyDeserializer(), workUnitJsonValueDeserializer());
        consumer.subscribe(Collections.singletonList(kafkaConsumerProperties.getTopic()));
        return consumer;
    }

    @Bean
    public Deserializer stringKeyDeserializer() {
        return new StringDeserializer();
    }

    @Bean
    public Deserializer workUnitJsonValueDeserializer() {
        return new JsonDeserializer(WorkUnit.class);
    }
}
