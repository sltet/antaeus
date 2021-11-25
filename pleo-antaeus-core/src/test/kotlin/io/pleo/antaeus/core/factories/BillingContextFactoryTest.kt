package io.pleo.antaeus.core.factories

import io.mockk.every
import io.mockk.mockk
import io.mockk.mockkStatic
import io.mockk.unmockkAll
import io.pleo.antaeus.core.jobs.BillingJob
import org.junit.jupiter.api.*
import org.quartz.JobBuilder
import org.quartz.ScheduleBuilder
import org.quartz.Trigger
import org.quartz.TriggerBuilder

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BillingContextFactoryTest {

    private val jobBuilder = mockk<JobBuilder>(relaxed = true)
    private val triggerBuilder = mockk<TriggerBuilder<Trigger>>(relaxed = true)
    private val scheduleBuilder = mockk<ScheduleBuilder<Trigger>>()
    private val billingContextFactory = BillingContextFactory()

    @BeforeAll
    fun setUp(){
        mockkStatic(JobBuilder::class)
        mockkStatic(TriggerBuilder::class)
    }

    @Test
    fun `will return a new billing context`() {
        val identity = "billing"
        val group = "group"
        every { JobBuilder.newJob(BillingJob::class.java) } returns jobBuilder
        every { TriggerBuilder.newTrigger() } returns triggerBuilder
        val billingContext = billingContextFactory.createBillingContext(identity, group, scheduleBuilder)
        Assertions.assertNotNull(billingContext)
        Assertions.assertNotNull(billingContext.detail)
        Assertions.assertNotNull(billingContext.trigger)
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }
}