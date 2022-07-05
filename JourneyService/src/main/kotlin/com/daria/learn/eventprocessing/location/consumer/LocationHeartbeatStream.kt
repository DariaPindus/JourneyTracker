package com.daria.learn.eventprocessing.location.consumer

import com.daria.learn.eventprocessing.location.domain.LocationHeartbeatEvent
import org.apache.kafka.common.serialization.Serde
import org.apache.kafka.common.serialization.Serdes
import org.apache.kafka.common.serialization.Serializer
import org.apache.kafka.streams.StreamsBuilder
import org.apache.kafka.streams.kstream.*
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.support.serializer.JsonDeserializer
import org.springframework.kafka.support.serializer.JsonSerializer


@Configuration
class LocationHeartbeatStream {

    @Value("\${topic.suspiciousActivity}")
    private val suspiciousActivityTopic = ""

    @Value("\${topic.locationHeartbeat}")
    private val locationHeartbeatTopic = ""

    private fun locationEventSerde(): Serde<LocationHeartbeatEvent> {
        val locationEventSerializer: Serializer<LocationHeartbeatEvent> = JsonSerializer()
        val locationEventDeserializer: JsonDeserializer<LocationHeartbeatEvent> =
            JsonDeserializer<LocationHeartbeatEvent>()
        locationEventDeserializer.addTrustedPackages("com.daria.learn.*")
        return Serdes.serdeFrom(locationEventSerializer, locationEventDeserializer)
    }

    @Bean
    fun kStream(kStreamBuilder: StreamsBuilder): KStream<String, String>? {
        val eventJsonStream: KStream<String, LocationHeartbeatEvent> = kStreamBuilder
            .stream(locationHeartbeatTopic, Consumed.with(Serdes.String(), locationEventSerde()))
        val stream =  eventJsonStream
            .groupBy { key, value -> key  }
//            .windowedBy(TimeWindows.ofSizeWithNoGrace(Duration.ofMillis(10000)))
            .count(Materialized.`as`("count-store"))
            .mapValues{ k,v -> k.toString() + " - " + v.toString()}
            .toStream()
        stream.to(suspiciousActivityTopic, Produced.with(Serdes.String(), Serdes.String()))
        return stream
    }


}