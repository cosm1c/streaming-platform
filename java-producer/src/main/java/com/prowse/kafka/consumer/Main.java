package com.prowse.kafka.consumer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.bridge.SLF4JBridgeHandler;

import java.io.IOException;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.LogManager;

public class Main {
    private static final Logger LOG = LoggerFactory.getLogger(ExampleProducer.class);
    private static final int DEFAULT_COUNT = 100;

    static {
        // Redirect all calls to Java Logging to SLF4J - we only want one logging system and prefer SLF4J
        LogManager.getLogManager().reset();
        SLF4JBridgeHandler.install();
        java.util.logging.Logger.getLogger("global").setLevel(Level.FINEST);
    }

    public static void main(final String[] args) throws IOException {
        final int count = parseCount(args);

        try (final Producer<String, String> producer = createProducer()) {
            LOG.info("Sending {} records", count);
            new ExampleProducer(producer)
                    .sendRecords(count);
        }

        LOG.info("Completed.");
    }

    private static Producer<String, String> createProducer() throws IOException {
        final Properties props = new Properties();
        props.load(ExampleProducer.class.getResourceAsStream("/producer.properties"));

        return new KafkaProducer<>(props);
    }

    private static int parseCount(final String[] args) {
        try {
            return Integer.parseInt(args[0]);
        } catch (final Throwable t) {
            LOG.info("No valid count provided as argument - defaulting to {}", DEFAULT_COUNT);
            return DEFAULT_COUNT;
        }
    }

}
