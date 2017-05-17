package sample.consumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sample.consumer.domain.WorkUnit;

public class WorkUnitsConsumer implements Runnable {
    private static final Logger log = LoggerFactory.getLogger(WorkUnitsConsumer.class);

    private final KafkaConsumer<String, WorkUnit> workUnitsConsumer;

    public WorkUnitsConsumer(KafkaConsumer<String, WorkUnit> workUnitsConsumer) {
        this.workUnitsConsumer = workUnitsConsumer;
    }

    public void run() {
        try {
            while (true) {
                ConsumerRecords<String, WorkUnit> records = this.workUnitsConsumer.poll(100);
                for (ConsumerRecord<String, WorkUnit> record : records) {
                    log.info("consuming from topic = {}, partition = {}, ts = {}, ts-type = {},  offset = {}, key = {}, value = {}",
                            record.topic(), record.partition(), record.timestamp(), record.timestampType(), record.offset(), record.key(), record.value());

                }
            }
        } finally {
            this.workUnitsConsumer.close();
        }
    }
}
