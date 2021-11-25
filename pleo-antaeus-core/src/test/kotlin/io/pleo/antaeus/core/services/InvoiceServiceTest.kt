package io.pleo.antaeus.core.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.pleo.antaeus.core.exceptions.InvoiceNotFoundException
import io.pleo.antaeus.data.AntaeusDal
import io.pleo.antaeus.models.Currency
import io.pleo.antaeus.models.Invoice
import io.pleo.antaeus.models.InvoiceStatus
import io.pleo.antaeus.models.Money
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.math.BigDecimal
import kotlin.random.Random

class InvoiceServiceTest {

    private val invoice = Invoice(1, Random.nextInt(0,500), Money(BigDecimal.valueOf(10), Currency.USD), InvoiceStatus.PENDING)
    private val invoices = arrayListOf(invoice)

    private val dal = mockk<AntaeusDal> {
        every { fetchInvoice(404) } returns null
        every { fetchInvoices() } returns invoices
    }

    private val invoiceService = InvoiceService(dal = dal)

    @Test
    fun `will return all invoices`(){
        val result = invoiceService.fetchAll()
        verify(exactly = 1) { dal.fetchInvoices() }
        assertEquals(invoices.size, result.size)
    }

    @Test
    fun `will return invoice given invoice id`(){
        every {dal.fetchInvoice(invoice.id)} returns invoice
        val result = invoiceService.fetch(invoice.id)
        verify(exactly = 1) { dal.fetchInvoice(invoice.id) }
        assertEquals(invoice, result)
    }

    @Test
    fun `will throw if invoice is not found`() {
        val invoiceId = 404
        assertThrows<InvoiceNotFoundException> {
            invoiceService.fetch(invoiceId)
            verify(exactly = 1) { dal.fetchInvoice(invoiceId) }
        }
    }

    @Test
    fun `will return invoices given a list of statuses`(){
        val statuses = emptySet<InvoiceStatus>()
        every {dal.fetchInvoices(any())} returns emptyList()
        val result = invoiceService.fetch(statuses)
        verify(exactly = 1) { dal.fetchInvoices(statuses) }
        assertEquals(statuses.size, result.size)
    }

    @Test
    fun `will update invoice`(){
        every {dal.updateInvoice(invoice)} returns invoice
        val result = invoiceService.update(invoice)
        verify(exactly = 1) { dal.updateInvoice(invoice) }
        assertEquals(invoice, result)
    }
}
