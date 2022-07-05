package com.daria.learn.eventprocessing.mock

import com.daria.learn.eventprocessing.journey.domain.EndJourneyEvent
import com.daria.learn.eventprocessing.journey.domain.StartJourneyEvent
import com.daria.learn.eventprocessing.location.domain.LocationHeartbeatEvent
import org.springframework.beans.factory.annotation.Value
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.util.*
import java.util.concurrent.ConcurrentHashMap
import kotlin.random.Random

@Component
class MockProducer(private val locationHeartbeatKafkaTemplate: KafkaTemplate<String, LocationHeartbeatEvent>,
                   private val startJourneyKafkaTemplate: KafkaTemplate<String, StartJourneyEvent>,
                   private val endJourneyKafkaTemplate: KafkaTemplate<String, EndJourneyEvent>,
) {

    private val temporaryJourneySet = Collections.newSetFromMap(ConcurrentHashMap<Long, Boolean>())

    @Value("\${topic.locationHeartbeat}")
    val locationHeartbeatTopic: String = ""
    @Value("\${topic.startJourney}")
    val startJourneyTopic: String = ""
    @Value("\${topic.endJourney}")
    val endJourneyTopic: String = ""

    @Scheduled(fixedDelay = 2000)
    fun sendLocationHeartbeatEvent() {
        if (temporaryJourneySet.isEmpty()) return
        val journeyId = temporaryJourneySet.elementAt(Random.nextInt(temporaryJourneySet.size))
        val event = LocationHeartbeatEvent(journeyId = journeyId)
        locationHeartbeatKafkaTemplate.send(locationHeartbeatTopic, event.journeyId.toString(), event)
        println("Sent: $event")
    }

    @Scheduled(fixedDelay = 5000)
    fun sendJourneyEvent() {
        val journeyId = Random.nextLong(0, 10)
        if (!(journeyId in temporaryJourneySet)) sendStartEvent(journeyId)
        else sendEndJourneyEvent(journeyId)
    }

    private fun sendStartEvent(journeyId: Long) {
        val event = StartJourneyEvent(journeyId = journeyId)
        startJourneyKafkaTemplate.send(startJourneyTopic, journeyId.toString(), event)
        println("Sent: $event")
        temporaryJourneySet.add(journeyId)
    }

    private fun sendEndJourneyEvent(journeyId: Long) {
        val event = EndJourneyEvent(journeyId = journeyId)
        endJourneyKafkaTemplate.send(endJourneyTopic, journeyId.toString(), event)
        println("Sent: $event")
        temporaryJourneySet.remove(journeyId)
    }
}