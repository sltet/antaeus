package io.pleo.antaeus.core.factories

import org.quartz.Scheduler
import org.quartz.impl.StdSchedulerFactory
import org.quartz.simpl.SimpleJobFactory

class SchedulerFactory {

    fun stdScheduler(jobFactory: SimpleJobFactory) : Scheduler {
        val scheduler = StdSchedulerFactory().scheduler
        scheduler.setJobFactory(jobFactory)
        return scheduler
    }
}