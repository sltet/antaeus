package io.pleo.antaeus.core.services

import io.pleo.antaeus.core.external.PaymentProvider
import io.pleo.antaeus.models.Invoice
import io.pleo.antaeus.models.InvoiceStatus
import mu.KotlinLogging

private val logger = KotlinLogging.logger {}

class BillingService(
    private val paymentProvider: PaymentProvider
) {

    fun chargeInvoice(invoice: Invoice): Invoice {
        try {
            if(paymentProvider.charge(invoice)){
                invoice.status = InvoiceStatus.PAID
            }
        } catch (ex: Exception) {
            logger.error(ex.message, ex)
        }
        return invoice
    }
}
