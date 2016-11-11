package sample.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.SmartLifecycle;
import org.springframework.stereotype.Component;
import sample.consumer.domain.WorkUnit;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Component
public class KafkaListener implements SmartLifecycle {

    private static final Logger LOGGER = LoggerFactory.getLogger(KafkaListener.class);


    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    private final KafkaConsumer<String, WorkUnit> kafkaWorkUnitsConsumer;

    private volatile boolean running = false;

    public KafkaListener(KafkaConsumer<String, WorkUnit> kafkaWorkUnitsConsumer) {
        this.kafkaWorkUnitsConsumer = kafkaWorkUnitsConsumer;
    }

    @Override
    public void start() {
        WorkUnitsConsumer workUnitsConsumer = new WorkUnitsConsumer(this.kafkaWorkUnitsConsumer);
        executorService.submit(workUnitsConsumer);
        this.running = true;
    }

    @Override
    public void stop() {
    }

    @Override
    public boolean isRunning() {
        return running;
    }

    @Override
    public boolean isAutoStartup() {
        return true;
    }

    @Override
    public void stop(Runnable callback) {

    }

    @Override
    public int getPhase() {
        return 0;
    }
}
