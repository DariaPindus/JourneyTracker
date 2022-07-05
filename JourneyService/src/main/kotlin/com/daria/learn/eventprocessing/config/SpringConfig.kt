package com.daria.learn.eventprocessing.config

import com.daria.learn.eventprocessing.location.domain.LocationHeartbeatEvent
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.scheduling.annotation.EnableScheduling


@Configuration
@EnableScheduling
class SpringConfig {

}