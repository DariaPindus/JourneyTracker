package com.daria.learn.eventprocessing.location.domain

import java.time.ZonedDateTime
import kotlin.random.Random

data class LocationHeartbeatEvent (val timestamp: ZonedDateTime = ZonedDateTime.now(),
                                   val journeyId: Long,
                                   val locationX: Double = Random.nextDouble(),
                                   val locationY: Double = Random.nextDouble()) {

    override fun toString(): String  = "[LocationHeartbeatEvent} $timestamp: $journeyId ($locationX, $locationY)"

}

