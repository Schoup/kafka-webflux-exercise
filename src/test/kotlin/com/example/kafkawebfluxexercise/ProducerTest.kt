package com.example.kafkawebfluxexercise

import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class ProducerTest {
    @Autowired
    private var producer: KafkaProducer? = null
    @Autowired
    private var consumer: KafkaConsumer? = null

/*    @Test
    fun testProducer() {
        producer.sendMessage("TESTY -- Hallo World!")
        assert(true)
    }

    @Test
    fun testConsumer() {
        consumer.listenGroupFoo("TESTY -- wtf")
        assert(true)
    }*/
}
