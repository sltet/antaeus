package io.pleo.antaeus.core.services

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import io.mockk.verify
import io.pleo.antaeus.core.exceptions.CurrencyMismatchException
import io.pleo.antaeus.core.exceptions.CustomerNotFoundException
import io.pleo.antaeus.core.exceptions.NetworkException
import io.pleo.antaeus.core.external.PaymentProvider
import io.pleo.antaeus.models.Currency
import io.pleo.antaeus.models.Invoice
import io.pleo.antaeus.models.InvoiceStatus
import io.pleo.antaeus.models.Money
import org.junit.jupiter.api.*
import org.junit.jupiter.api.Assertions.assertEquals
import java.math.BigDecimal
import kotlin.random.Random

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BillingServiceTest {

    private val invoice = Invoice(1, Random.nextInt(0,500), Money(BigDecimal.valueOf(10), Currency.USD), InvoiceStatus.PENDING)

    @RelaxedMockK
    lateinit var paymentProvider : PaymentProvider

    private lateinit var billingService : BillingService

    @BeforeAll
    fun setUp(){
        MockKAnnotations.init(this, relaxUnitFun = true)
        billingService = BillingService(paymentProvider)
    }

    @Test
    fun `will successfully charge the invoice`() {
        every { paymentProvider.charge(invoice) } returns true
        val chargedInvoice = billingService.chargeInvoice(invoice)
        verify {paymentProvider.charge(invoice)}
        assertEquals(InvoiceStatus.PAID, chargedInvoice.status)
    }

    @Test
    fun `will throw customer not found exception and return unpaid invoice`() {
        every { paymentProvider.charge(invoice) } throws CustomerNotFoundException(invoice.customerId)
        val chargedInvoice = billingService.chargeInvoice(invoice)
        verify { paymentProvider.charge(invoice) }
        assertEquals(InvoiceStatus.PENDING, chargedInvoice.status)
    }

    @Test
    fun `will throw Currency mismatch exception and return unpaid invoice`() {
        every { paymentProvider.charge(invoice) } throws CurrencyMismatchException(invoice.id, invoice.customerId)
        val chargedInvoice = billingService.chargeInvoice(invoice)
        verify { paymentProvider.charge(invoice) }
        assertEquals(InvoiceStatus.PENDING, chargedInvoice.status)
    }

    @Test
    fun `will throw network exception and return unpaid invoice`() {
        every { paymentProvider.charge(invoice) } throws NetworkException()
        val chargedInvoice = billingService.chargeInvoice(invoice)
        verify { paymentProvider.charge(invoice) }
        assertEquals(InvoiceStatus.PENDING, chargedInvoice.status)
    }

    @AfterAll
    fun tearDown() {
        unmockkAll()
    }
}