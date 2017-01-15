package com.prowse.kafka.consumer;

import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.time.Instant;

import static java.util.Objects.requireNonNull;

public final class ExampleProducer {

    private final Producer<String, String> producer;

    public ExampleProducer(final Producer<String, String> producer) {
        this.producer = requireNonNull(producer);
    }

    public void sendRecords(final int count) {
        for (int recordKey = 0; recordKey < count; recordKey++) {
            producer.send(new ProducerRecord<>("Topic1", Integer.toString(recordKey), Instant.now().toString()));
        }
    }
}
