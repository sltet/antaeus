package io.pleo.antaeus.core.services

import io.pleo.antaeus.core.factory.BillingJobFactory
import io.pleo.antaeus.core.jobs.Billing
import org.quartz.Scheduler

class QuartzSchedulingService(billings: ArrayList<Billing>, private val scheduler: Scheduler) : SchedulingService {

    init {
        billings.forEach {
            billing -> scheduler.scheduleJob(billing.detail, billing.trigger)
        }
    }

    override fun start(){
        scheduler.start()
    }

}