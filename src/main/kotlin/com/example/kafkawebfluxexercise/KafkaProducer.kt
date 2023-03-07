package com.example.kafkawebfluxexercise

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

@Component
class KafkaProducer {
    @Autowired
    private lateinit var kafkaTemplate: KafkaTemplate<String, String>
    private val LOGGER: Logger = LoggerFactory.getLogger(KafkaProducer::class.java)
    fun send(topic: String, payload: String) {
        LOGGER.info("sending payload='{}' to topic='{}'", payload, topic)
        kafkaTemplate.send(topic, payload)
    }
}