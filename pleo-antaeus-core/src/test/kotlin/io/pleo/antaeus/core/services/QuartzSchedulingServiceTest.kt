package io.pleo.antaeus.core.services

import io.mockk.*
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.pleo.antaeus.core.model.BillingContext
import org.junit.jupiter.api.AfterAll
import org.junit.jupiter.api.BeforeAll
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.TestInstance
import org.quartz.JobDetail
import org.quartz.Scheduler
import org.quartz.Trigger

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class QuartzSchedulingServiceTest {

    private val jobDetail = mockk<JobDetail>()
    private val jobTrigger = mockk<Trigger>()

    private val billingContext = mockk<BillingContext> {
        every { detail } returns jobDetail
        every { trigger } returns jobTrigger
    }

    private val billingContexts = arrayListOf(billingContext)

    @RelaxedMockK
    lateinit var scheduler: Scheduler

    private lateinit var schedulingService: SchedulingService

    @BeforeAll
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        schedulingService = QuartzSchedulingService(billingContexts, scheduler)
    }

    @Test
    fun `will start the scheduler`() {
        schedulingService.start()
        verify { scheduler.start() }
    }

    @Test
    fun `will schedule all billing contexts`() {
        schedulingService.start()
        verify(exactly = 1) { billingContext.detail }
        verify(exactly = 1) { billingContext.trigger }
        verify { scheduler.start() }
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }
}