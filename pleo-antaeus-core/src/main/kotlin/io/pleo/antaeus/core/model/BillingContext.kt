package io.pleo.antaeus.core.model

import org.quartz.JobDetail
import org.quartz.Trigger

data class BillingContext(var detail: JobDetail, var trigger: Trigger)