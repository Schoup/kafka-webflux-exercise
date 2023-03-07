package com.example.kafkawebfluxexercise

import org.apache.kafka.clients.admin.AdminClientConfig
import org.apache.kafka.clients.admin.NewTopic
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource
import org.springframework.kafka.core.KafkaAdmin


@Configuration
@PropertySource("classpath:application.properties")
@ConfigurationProperties(prefix = "spring.kafka")
class KafkaTopicConfig {
    @Value("\${bootstrap-servers}")
    private var bootstrapAddress: String = ""

    @Bean
    fun kafkaAdmin(): KafkaAdmin {
        var configs:MutableMap<String, Any> = HashMap()
        configs[AdminClientConfig.BOOTSTRAP_SERVERS_CONFIG] = bootstrapAddress
        return KafkaAdmin(configs)
    }

    @Bean
    fun topic1(): NewTopic {
        return NewTopic("my-first-topic", 1, 1.toShort())
    }
}