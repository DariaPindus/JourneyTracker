package com.daria.learn.eventprocessing.journey.domain

import java.time.ZonedDateTime
import kotlin.random.Random

class StartJourneyEvent(private val timestamp: ZonedDateTime = ZonedDateTime.now(),
                        private val journeyId: Long,
                        private val startLocation: Location = Location(Random.nextDouble(), Random.nextDouble()),
                        private val endLocation: Location = Location(Random.nextDouble(), Random.nextDouble())) {

    override fun toString(): String  = "[StartJourneyEvent] $timestamp: $journeyId $startLocation -> $endLocation"

}