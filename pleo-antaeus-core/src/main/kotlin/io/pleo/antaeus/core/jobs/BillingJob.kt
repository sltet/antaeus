package io.pleo.antaeus.core.jobs

import io.pleo.antaeus.core.services.BillingService
import io.pleo.antaeus.core.services.InvoiceService
import org.quartz.Job
import org.quartz.JobExecutionContext

class BillingJob : Job {

    lateinit var billingService: BillingService
    lateinit var invoiceService: InvoiceService

    override fun execute(context: JobExecutionContext?) {
        // fetch NOT_PAID invoices
        // bill
        // update invoice and save
        print("Job execution")
    }

}