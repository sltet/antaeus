package io.pleo.antaeus.core.factory

import io.pleo.antaeus.core.jobs.BillingContext
import io.pleo.antaeus.core.jobs.BillingJob
import org.quartz.*

class BillingContextFactory {

    fun createBillingContext(identity: String, group: String, scheduleBuilder: ScheduleBuilder<SimpleTrigger>) : BillingContext {

        val billingJob = JobBuilder.newJob(BillingJob::class.java)
            .withIdentity(identity, group)
            .build()

        val billingTrigger = TriggerBuilder.newTrigger().forJob(billingJob)
            .withIdentity(identity)
            .withDescription("$identity trigger")
            .withSchedule(scheduleBuilder)
            .build()

        return BillingContext(billingJob, billingTrigger)
    }
}