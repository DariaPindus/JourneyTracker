package com.daria.learn.eventprocessing.config

import com.daria.learn.eventprocessing.journey.domain.EndJourneyEvent
import com.daria.learn.eventprocessing.journey.domain.StartJourneyEvent
import com.daria.learn.eventprocessing.location.domain.LocationHeartbeatEvent
import org.apache.kafka.common.serialization.*
import org.apache.kafka.streams.StreamsConfig.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.annotation.EnableKafkaStreams
import org.springframework.kafka.annotation.KafkaStreamsDefaultConfiguration
import org.springframework.kafka.config.KafkaStreamsConfiguration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import java.util.Map


@Configuration
@EnableKafka
@EnableKafkaStreams
class KafkaConfig {

    @Value(value = "\${kafka.bootstrapServersConfig}")
    private val bootstrapAddress: String = ""

    @Bean(name = [KafkaStreamsDefaultConfiguration.DEFAULT_STREAMS_CONFIG_BEAN_NAME])
    fun kStreamsConfig(): KafkaStreamsConfiguration {
        // taken from https://github.com/apache/kafka/blob/1.0/streams/examples/src/main/java/org/apache/kafka/streams/examples/pageview/PageViewTypedDemo.java
        return KafkaStreamsConfiguration(
            Map.of<String, Any>(
                APPLICATION_ID_CONFIG, "journey-service",
                BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress,
                DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().javaClass.name,
                DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().javaClass.name
//                DEFAULT_TIMESTAMP_EXTRACTOR_CLASS_CONFIG, WallclockTimestampExtractor::class.java.name
            )
        )
    }

    @Bean
    fun locationHeartbeatKafkaTemplate(kafkaProducerFactory: ProducerFactory<String, LocationHeartbeatEvent>): KafkaTemplate<String, LocationHeartbeatEvent>? {
        return KafkaTemplate(kafkaProducerFactory)
    }

    @Bean
    fun startJourneyKafkaTemplate(kafkaProducerFactory: ProducerFactory<String, StartJourneyEvent>): KafkaTemplate<String, StartJourneyEvent>? {
        return KafkaTemplate(kafkaProducerFactory)
    }

    @Bean
    fun endJourneyKafkaTemplate(kafkaProducerFactory: ProducerFactory<String, EndJourneyEvent>): KafkaTemplate<String, EndJourneyEvent>? {
        return KafkaTemplate(kafkaProducerFactory)
    }

}