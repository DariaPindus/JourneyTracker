package com.daria.learn.eventprocessing.journey.domain

import java.time.ZonedDateTime

class EndJourneyEvent(val timestamp: ZonedDateTime = ZonedDateTime.now(),
                      val journeyId: Long
) {

    override fun toString(): String  = "[EndJourneyEvent] $timestamp: $journeyId"

}