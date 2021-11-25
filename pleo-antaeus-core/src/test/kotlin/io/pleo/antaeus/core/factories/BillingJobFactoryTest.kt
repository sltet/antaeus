package io.pleo.antaeus.core.factories

import io.mockk.every
import io.mockk.mockk
import io.pleo.antaeus.core.jobs.BillingJob
import io.pleo.antaeus.core.services.BillingService
import io.pleo.antaeus.core.services.InvoiceService
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.spi.TriggerFiredBundle

class BillingJobFactoryTest {

    private val jobDetail = mockk<JobDetail>()
    private val bundle = mockk<TriggerFiredBundle>()

    private val scheduler = mockk<Scheduler>()
    private val billingService = mockk<BillingService>()
    private val invoiceService = mockk<InvoiceService>()

    private val billingJobFactory = BillingJobFactory(billingService, invoiceService)

    @Test
    fun `will return a new billing job`() {
        every { jobDetail.jobClass } returns BillingJob::class.java
        every { bundle.jobDetail } returns jobDetail
        val billingJob = billingJobFactory.newJob(bundle, scheduler) as BillingJob
        Assertions.assertNotNull(billingJob)
        Assertions.assertNotNull(billingJob.billingService)
        Assertions.assertNotNull(billingJob.invoiceService)
    }
}