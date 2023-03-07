package com.example.kafkawebfluxexercise

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Component

//@Component
class Producer {
    @Autowired
    val kafkaTemplate: KafkaTemplate<String, String>? = null

    fun sendMessage(msg: String) {
        kafkaTemplate!!.send("my-first-topic", msg)
    }
}