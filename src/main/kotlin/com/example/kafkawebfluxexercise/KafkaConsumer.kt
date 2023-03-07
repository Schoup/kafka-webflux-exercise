package com.example.kafkawebfluxexercise

import org.apache.kafka.clients.consumer.ConsumerRecord
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch


@Component
class KafkaConsumer {
    private val LOGGER: Logger = LoggerFactory.getLogger(KafkaConsumer::class.java)

    private var latch = CountDownLatch(1)
    private lateinit var payload: String

    @KafkaListener(topics = ["\${test.topic}"])
    fun receive(consumerRecord: ConsumerRecord<*, *>) {
        LOGGER.info("received payload='{}'", consumerRecord.toString())
        payload = consumerRecord.toString()
        latch.countDown()
    }

    fun resetLatch() {
        latch = CountDownLatch(1)
    }

    fun getLatch(): CountDownLatch {
        return latch
    }

    fun getPayload(): String {
        return payload
    }

/*    @KafkaListener(topics = ["my-first-topic"], groupId = "foo")
    fun listenGroupFoo(message: String) {
        println("Received Message in group foo: $message")
    }

    @KafkaListener(topics = ["my-first-topic"])
    fun listenWithHeaders(
    @Payload message:String,
    @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int) {
        println("Received Message: " + message + "from partition: " + partition)
    }*/
}