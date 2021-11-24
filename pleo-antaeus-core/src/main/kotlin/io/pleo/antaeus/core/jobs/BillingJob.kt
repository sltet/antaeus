package io.pleo.antaeus.core.jobs

import io.pleo.antaeus.core.services.BillingService
import io.pleo.antaeus.core.services.InvoiceService
import io.pleo.antaeus.models.InvoiceStatus
import org.quartz.Job
import org.quartz.JobExecutionContext

class BillingJob : Job {

    lateinit var billingService: BillingService
    lateinit var invoiceService: InvoiceService

    override fun execute(context: JobExecutionContext?) {
        print("Job execution")
        invoiceService.fetch(setOf(InvoiceStatus.PENDING))
            .stream()
            .map { invoice ->  billingService.charge(invoice) }
            .filter { invoice -> invoice.status == InvoiceStatus.PAID }
            .peek { invoice -> invoiceService.update(invoice)}
            .forEach { invoice -> print("updated invoice with id ${invoice.id}") } // TODO proper logging
    }

}