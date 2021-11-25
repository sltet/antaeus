package io.pleo.antaeus.core.jobs

import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import io.pleo.antaeus.core.services.BillingService
import io.pleo.antaeus.core.services.InvoiceService
import io.pleo.antaeus.models.Invoice
import io.pleo.antaeus.models.InvoiceStatus
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.quartz.JobExecutionContext

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BillingJobTest {

    private val invoice = mockk<Invoice>(relaxed = true)
    private val jobExecutionContext = mockk<JobExecutionContext>()

    @RelaxedMockK
    lateinit var billingService : BillingService
    @RelaxedMockK
    lateinit var invoiceService : InvoiceService

    private lateinit var billingJob : BillingJob

    @BeforeAll
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        billingJob = BillingJob()
        billingJob.billingService = billingService
        billingJob.invoiceService = invoiceService
    }

    @Test
    fun `will execute the billing job`() {
        val invoiceStatuses = setOf(InvoiceStatus.PENDING)
        val invoices = listOf(invoice)
        every { invoice.status } returns InvoiceStatus.PAID
        every { invoiceService.fetch(any<Set<InvoiceStatus>>()) } returns invoices
        every { billingService.chargeInvoice(invoice) } returns invoice
        billingJob.execute(jobExecutionContext)
        verify { invoiceService.fetch(invoiceStatuses) }
        verify { billingService.chargeInvoice(invoice) }
        verify { invoiceService.update(invoice) }
    }

    @AfterAll
    fun teardown(){
        unmockkAll()
    }
}