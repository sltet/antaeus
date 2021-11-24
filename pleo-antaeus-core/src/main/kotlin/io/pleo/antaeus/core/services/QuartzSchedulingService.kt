package io.pleo.antaeus.core.services

import io.pleo.antaeus.core.jobs.Billing
import io.pleo.antaeus.core.factory.BillingJobFactory
import org.quartz.impl.StdSchedulerFactory

class QuartzSchedulingService(billings: ArrayList<Billing>, billingFactory: BillingJobFactory) : SchedulingService {

    private val scheduler = StdSchedulerFactory().scheduler

    init {
        billings.forEach {
            billing -> scheduler.scheduleJob(billing.detail, billing.trigger)
        }
        scheduler.setJobFactory(billingFactory)
    }

    override fun start(){
        scheduler.start()
    }

}