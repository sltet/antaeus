package io.pleo.antaeus.core.services

import io.pleo.antaeus.core.model.BillingContext
import org.quartz.Scheduler

class QuartzSchedulingService(billingContexts: ArrayList<BillingContext>, private val scheduler: Scheduler) : SchedulingService {

    init {
        billingContexts.forEach {
            billingContext -> scheduler.scheduleJob(billingContext.detail, billingContext.trigger)
        }
    }

    override fun start(){
        scheduler.start()
    }

}