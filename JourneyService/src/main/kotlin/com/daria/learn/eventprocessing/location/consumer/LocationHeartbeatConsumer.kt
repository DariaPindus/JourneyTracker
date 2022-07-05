package com.daria.learn.eventprocessing.location.consumer

import com.daria.learn.eventprocessing.location.domain.LocationHeartbeatEvent
import org.springframework.context.annotation.Profile
import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component

@Component
@Profile("simple-consumer")
class LocationHeartbeatConsumer {

//    @KafkaListener(id="locationHeartbeat", topics = ["#{'\${topic.locationHeartbeat}'}"])
    fun consume(event: LocationHeartbeatEvent) {
        println("Received: $event")
    }

}