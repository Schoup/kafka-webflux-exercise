package com.example.kafkawebfluxexercise

import org.apache.kafka.clients.admin.NewTopic
import org.springframework.context.annotation.Bean
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.kafka.config.TopicBuilder
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload
import org.springframework.stereotype.Component


@Component
class Consumer {
    @KafkaListener(topics = ["my-first-topic"], groupId = "foo")
    fun listenGroupFoo(message: String) {
        println("Received Message in group foo: $message")
    }

    @KafkaListener(topics = ["my-first-topic"])
    fun listenWithHeaders(
    @Payload message:String,
    @Header(KafkaHeaders.RECEIVED_PARTITION) partition: Int) {
        println("Received Message: " + message + "from partition: " + partition)
    }
}