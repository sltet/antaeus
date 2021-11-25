package io.pleo.antaeus.core.factories

import io.mockk.mockk
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.quartz.simpl.SimpleJobFactory

class SchedulerFactoryTest {

    private val jobFactory = mockk<SimpleJobFactory>()

    private val schedulerFactory = SchedulerFactory()

    @Test
    fun `will return a std scheduler`() {
        val scheduler = schedulerFactory.stdScheduler(jobFactory)
        assertNotNull(scheduler)
    }
}