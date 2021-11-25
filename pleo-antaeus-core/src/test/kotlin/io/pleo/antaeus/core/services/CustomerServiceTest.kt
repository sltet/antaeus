package io.pleo.antaeus.core.services

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.pleo.antaeus.core.exceptions.CustomerNotFoundException
import io.pleo.antaeus.data.AntaeusDal
import io.pleo.antaeus.models.Currency
import io.pleo.antaeus.models.Customer
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import kotlin.random.Random

class CustomerServiceTest {
    private val dal = mockk<AntaeusDal> {
        every { fetchCustomer(404) } returns null
    }

    private val customerService = CustomerService(dal = dal)

    @Test
    fun `will throw if customer is not found`() {
        assertThrows<CustomerNotFoundException> {
            customerService.fetch(404)
        }
    }

    @Test
    fun `will return all customers`(){
        val expectedCustomers = emptyList<Customer>()
        every { dal.fetchCustomers() } returns expectedCustomers
        val actualCustomers = customerService.fetchAll()
        verify(exactly = 1) { dal.fetchCustomers() }
        Assertions.assertEquals(expectedCustomers.size, actualCustomers.size)
    }

    @Test
    fun `will return customer given customer id`(){
        val expectedCustomer = Customer(Random.nextInt(1,100), Currency.USD)
        every { dal.fetchCustomer(expectedCustomer.id) } returns expectedCustomer
        val actualCustomer = customerService.fetch(expectedCustomer.id)
        verify(exactly = 1) { dal.fetchCustomer(expectedCustomer.id) }
        Assertions.assertEquals(expectedCustomer, actualCustomer)
    }
}
