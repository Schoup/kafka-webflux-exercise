package com.example.kafkawebfluxexercise

import junit.framework.Assert
import org.hamcrest.Matchers
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.kafka.test.context.EmbeddedKafka
import org.springframework.test.annotation.DirtiesContext
import java.util.concurrent.TimeUnit

@SpringBootTest
@DirtiesContext
@EmbeddedKafka(partitions = 1, brokerProperties = ["listeners=PLAINTEXT://localhost:9092", "port=9092"])
internal class EmbeddedKafkaIntegrationTest {
    @Autowired
    private lateinit var consumer: KafkaConsumer

    @Autowired
    private lateinit var producer: KafkaProducer

    @Value("\${test.topic}")
    private lateinit var topic: String
    @Test
    @Throws(Exception::class)
    fun givenEmbeddedKafkaBroker_whenSendingWithSimpleProducer_thenMessageReceived() {
        val data = "Sending with our own simple KafkaProducer"
        producer.send(topic, data)
        val messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS)
        Assert.assertTrue(messageConsumed)
        org.junit.Assert.assertThat(consumer.getPayload(), Matchers.containsString(data))
    }
}