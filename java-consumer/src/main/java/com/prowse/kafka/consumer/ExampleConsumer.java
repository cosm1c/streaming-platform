package com.prowse.kafka.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;

import static java.util.Objects.requireNonNull;

public final class ExampleConsumer {

    private final Consumer<String, String> consumer;
    private static final long TIMEOUT = Long.MAX_VALUE;

    public ExampleConsumer(Consumer<String, String> consumer) {
        this.consumer = requireNonNull(consumer);
    }

    public void logRecords(final int count, final Logger logger) {
        int total = 0;
        do {
            int received = consumer.poll(TIMEOUT).count();
            logger.info("Received {} records", received);
            total += received;
        } while (total < count);
    }
}
