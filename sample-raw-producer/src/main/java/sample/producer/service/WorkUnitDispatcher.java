package sample.producer.service;


import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sample.producer.config.KafkaProducerProperties;
import sample.producer.domain.WorkUnit;

@Service
public class WorkUnitDispatcher {

    @Autowired
    private KafkaProducer<String, WorkUnit> workUnitProducer;

    @Autowired
    private KafkaProducerProperties kafkaProducerProperties;

    private static final Logger LOGGER = LoggerFactory.getLogger(WorkUnitDispatcher.class);

    public boolean dispatch(WorkUnit workUnit) {
        ProducerRecord<String, WorkUnit> record =
                new ProducerRecord<>(kafkaProducerProperties.getTopic(), workUnit.getId(), workUnit);
        try {
            RecordMetadata recordMetadata = this.workUnitProducer.send(record).get();
            LOGGER.info("topic = {}, partition = {}, offset = {}, workUnit = {}",
                    recordMetadata.topic(), recordMetadata.partition(), recordMetadata.offset(), workUnit);
            return true;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
