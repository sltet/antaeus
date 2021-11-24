package io.pleo.antaeus.core.services

import io.pleo.antaeus.core.exceptions.CurrencyMismatchException
import io.pleo.antaeus.core.exceptions.CustomerNotFoundException
import io.pleo.antaeus.core.exceptions.NetworkException
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
        } catch (e: CustomerNotFoundException) {
            logger.error("Customer with id ${invoice.customerId} does not exists", e)
        } catch (e: CurrencyMismatchException) {
            logger.error("Customer currency with id ${invoice.customerId} does not match the customer account", e)
        } catch (e: NetworkException) {
            logger.error("Unexpected network exception", e)
        } catch (e: Exception) {
            logger.error(e.message, e)
        }
        return invoice
    }
}
