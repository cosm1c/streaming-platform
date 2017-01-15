package com.prowse.kafka.consumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(Main.class);
    private static final int DEFAULT_COUNT = 100;
    private static final List<String> TOPICS = Arrays.asList("Topic1");

    static {
        // Redirect all calls to Java Logging to SLF4J - we only want one logging system and prefer SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("global").setLevel(Level.FINEST);
    }


    public static void main(final String[] args) throws IOException {
        final int count = parseCount(args);

        try (final KafkaConsumer<String, String> consumer = createConsumer()) {
            consumer.subscribe(TOPICS);

            LOG.info("Consuming {} records", count);
            new ExampleConsumer(consumer)
                    .logRecords(count, LoggerFactory.getLogger(ExampleConsumer.class));
        }
        LOG.info("Completed.");
    }

    private static KafkaConsumer<String, String> createConsumer() throws IOException {
        final Properties props = new Properties();
        props.load(Main.class.getResourceAsStream("/consumer.properties"));

        return new KafkaConsumer<>(props);
    }

    private static int parseCount(final String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (final Throwable t) {
            LOG.info("No valid count provided as argument - using default");
            return DEFAULT_COUNT;
        }
    }

}
