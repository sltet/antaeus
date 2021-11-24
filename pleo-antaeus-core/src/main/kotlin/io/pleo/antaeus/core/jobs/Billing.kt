package io.pleo.antaeus.core.jobs

import org.quartz.JobDetail
import org.quartz.Trigger

data class Billing(var detail: JobDetail, var trigger: Trigger)