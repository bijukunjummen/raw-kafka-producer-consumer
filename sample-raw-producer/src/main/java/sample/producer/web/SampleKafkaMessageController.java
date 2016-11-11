package sample.producer.web;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import sample.producer.config.KafkaProducerProperties;
import sample.producer.domain.WorkUnit;

@RestController
public class SampleKafkaMessageController {

    @Autowired
    private KafkaProducer<String, WorkUnit> workUnitProducer;

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(SampleKafkaMessageController.class);

    @GetMapping("/generateWork")
    public String sendMessage(WorkUnit workUnit) {
        ProducerRecord<String, WorkUnit> record =
                new ProducerRecord<>(kafkaProducerProperties.getTopic(), workUnit.getId(), workUnit);
        try {
            RecordMetadata recordMetadata = this.workUnitProducer.send(record).get();
            return String.format("topic = %s, partition = %s, offset = %s",
                    recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset());
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

    }
}
