package io.pleo.antaeus.core.jobs

import io.pleo.antaeus.core.services.BillingService
import io.pleo.antaeus.core.services.InvoiceService
import io.pleo.antaeus.models.InvoiceStatus
import mu.KotlinLogging
import org.quartz.Job
import org.quartz.JobExecutionContext

private val logger = KotlinLogging.logger {}

class BillingJob : Job {

    lateinit var billingService: BillingService
    lateinit var invoiceService: InvoiceService

    override fun execute(context: JobExecutionContext?) {
        invoiceService.fetch(setOf(InvoiceStatus.PENDING))
            .stream()
            .map { invoice ->  billingService.chargeInvoice(invoice) }
            .filter { invoice -> invoice.status == InvoiceStatus.PAID }
            .map { invoice -> invoiceService.update(invoice)}
            .forEach { invoice -> logger.debug("Successfully charged invoice with id ${invoice.id}") }
    }

}