package com.example.kafkawebfluxexercise

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.hamcrest.CoreMatchers.containsString
import org.hamcrest.MatcherAssert.assertThat
import org.junit.ClassRule
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Import
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*
import org.springframework.test.annotation.DirtiesContext
import org.springframework.test.context.junit4.SpringRunner
import org.testcontainers.containers.KafkaContainer
import org.testcontainers.utility.DockerImageName
import java.util.concurrent.TimeUnit


@RunWith(SpringRunner::class)
@Import(KafkaTestContainersLiveTest.KafkaTestContainersConfiguration::class)
@SpringBootTest(classes = [KafkaWebfluxExerciseApplication::class])
@DirtiesContext
class KafkaTestContainersLiveTest {
    @Autowired
    private lateinit var consumer: KafkaConsumer

    @Autowired
    private lateinit var producer: KafkaProducer

    @Value("\${test.topic}")
    private lateinit var topic: String
    @Test
    @Throws(Exception::class)
    fun givenKafkaDockerContainer_whenSendingWithSimpleProducer_thenMessageReceived() {
        val data = "Sending with our own simple KafkaProducer"
        producer.send(topic, data)
        val messageConsumed = consumer.getLatch().await(10, TimeUnit.SECONDS)
        assertTrue(messageConsumed)
        assertThat(consumer.getPayload(), containsString(data))
    }

    companion object {
        @JvmField
        @ClassRule
        var kafka: KafkaContainer = KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"))
    }

    @TestConfiguration
    class KafkaTestContainersConfiguration {
        @Bean
        fun kafkaListenerContainerFactory(): ConcurrentKafkaListenerContainerFactory<Int, String> {
            val factory = ConcurrentKafkaListenerContainerFactory<Int, String>()
            factory.setConsumerFactory(consumerFactory())
            return factory
        }

        @Bean
        fun consumerFactory(): ConsumerFactory<Int, String> {
            return DefaultKafkaConsumerFactory(consumerConfigs())
        }

        @Bean
        fun consumerConfigs(): Map<String, Any> {
            val props: MutableMap<String, Any> = HashMap()
            props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = KafkaTestContainersLiveTest.kafka.bootstrapServers
            props[ConsumerConfig.AUTO_OFFSET_RESET_CONFIG] = "earliest"
            props[ConsumerConfig.GROUP_ID_CONFIG] = "baeldung"
            props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
            props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
            return props
        }

        @Bean
        fun producerFactory(): ProducerFactory<String, String> {
            val configProps: MutableMap<String, Any> = HashMap()
            configProps[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = KafkaTestContainersLiveTest.kafka.bootstrapServers
            configProps[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
            configProps[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
            return DefaultKafkaProducerFactory(configProps)
        }

        @Bean
        fun kafkaTemplate(): KafkaTemplate<String, String> {
            return KafkaTemplate(producerFactory())
        }
    }
}