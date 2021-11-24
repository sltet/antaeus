package io.pleo.antaeus.core.factory

import io.pleo.antaeus.core.jobs.BillingJob
import io.pleo.antaeus.core.services.BillingService
import io.pleo.antaeus.core.services.InvoiceService
import org.quartz.Job
import org.quartz.Scheduler
import org.quartz.simpl.SimpleJobFactory
import org.quartz.spi.TriggerFiredBundle

class BillingJobFactory(private val billingService: BillingService,
                        private val invoiceService: InvoiceService
                         ) : SimpleJobFactory() {
    override fun newJob(bundle: TriggerFiredBundle?, Scheduler: Scheduler?): Job {
        val billingJob = super.newJob(bundle, Scheduler) as BillingJob
        billingJob.billingService = billingService
        billingJob.invoiceService = invoiceService
        return billingJob
    }

}