package io.pleo.antaeus.core.services

import io.pleo.antaeus.core.exceptions.CurrencyMismatchException
import io.pleo.antaeus.core.exceptions.CustomerNotFoundException
import io.pleo.antaeus.core.exceptions.NetworkException
import io.pleo.antaeus.core.external.PaymentProvider
import io.pleo.antaeus.models.Invoice
import io.pleo.antaeus.models.InvoiceStatus

class BillingService(
    private val paymentProvider: PaymentProvider
) {

    fun chargeInvoice(invoice: Invoice): Invoice {
        try {
            if(paymentProvider.charge(invoice)){
                invoice.status = InvoiceStatus.PAID
            }
        } catch (e: CustomerNotFoundException) {
            // TODO error logging
        } catch (e: CurrencyMismatchException) {
            // TODO error logging
        } catch (e: NetworkException) {
            // TODO error logging
        } catch (e: Exception) {
            // TODO error logging
        }
        return invoice
    }
}
