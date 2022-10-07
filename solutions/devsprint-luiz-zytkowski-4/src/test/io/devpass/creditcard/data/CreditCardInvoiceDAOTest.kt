package io.devpass.creditcard.data

import io.devpass.creditcard.data.entities.CreditCardInvoiceEntity
import io.devpass.creditcard.data.repositories.CreditCardInvoiceRepository
import io.devpass.creditcard.domain.objects.CreditCardInvoice
import io.mockk.every
import io.mockk.mockk
import java.time.LocalDateTime
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import io.mockk.every
import io.mockk.mockk
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class CreditCardInvoiceDAOTest {

    @Test
    fun `Should return CreditCardInvoice`() {
        val creditCardInvoiceEntityList = getListOfCreditCardInvoiceEntity()
        val creditCardInvoiceReference = creditCardInvoiceEntityList.first().toCreditCardInvoice()

        val creditCardInvoiceRepository = mockk<CreditCardInvoiceRepository> {
            every { getByPeriod(any(), any(), any()) } returns creditCardInvoiceEntityList
        }

        val creditCardInvoiceDAO = CreditCardInvoiceDAO(creditCardInvoiceRepository)
        val result = creditCardInvoiceDAO.getByPeriod("", 0, 0)

        assertEquals(creditCardInvoiceReference, result)
    }

    @Test
    fun `Should return null if return an empty list fro Repository`() {
        val creditCardInvoiceRepository = mockk<CreditCardInvoiceRepository> {
            every { getByPeriod(any(), any(), any()) } returns emptyList()
        }
        val creditCardInvoiceDAO = CreditCardInvoiceDAO(creditCardInvoiceRepository)
        val result = creditCardInvoiceDAO.getByPeriod("", 0, 0)

        assertNull(result)
    }

    @Test
    fun `Should leak an exception when getByPeriod throws an exception himself`() {
        val creditCardInvoiceRepository = mockk<CreditCardInvoiceRepository> {
            every { getByPeriod(any(), any(), any()) } throws Exception()
        }
        val creditCardInvoiceDAO = CreditCardInvoiceDAO(creditCardInvoiceRepository)
        assertThrows<Exception> {
            creditCardInvoiceDAO.getByPeriod("", 0, 0)
        }
    }
    
    @Test
    fun `should call create method and return a CreditCardInvoice`(){
        val creditCardInvoiceEntity = getRandomCreditCardInvoiceEntity()

        val creditCardInvoiceRepository = mockk<CreditCardInvoiceRepository>(){
            every { save(any()) } returns creditCardInvoiceEntity
        }

        val creditCardInvoiceDAO = CreditCardInvoiceDAO(creditCardInvoiceRepository)

        val result = creditCardInvoiceDAO.create(creditCardInvoice = creditCardInvoiceEntity.toCreditCardInvoice())

        Assertions.assertEquals(creditCardInvoiceEntity.toCreditCardInvoice(), result)
    }

    private fun getListOfCreditCardInvoiceEntity(): List<CreditCardInvoiceEntity> {
        return listOf(
            CreditCardInvoiceEntity(
                id = "",
                creditCard = "",
                month = 0,
                year = 0,
                value = 0.0
            )
        )
    }

    private fun getCreditCardInvoice(): CreditCardInvoice {
        return CreditCardInvoice(
            id = "",
            creditCard = "",
            month = 0,
            year = 0,
            value = 0.0,
            createdAt = LocalDateTime.now(),
            paidAt = null
       )
    }
   

    fun getRandomCreditCardInvoiceEntity() : CreditCardInvoiceEntity{
        return CreditCardInvoiceEntity(
            id = "",
            creditCard = "",
            month = 1,
            year = 2022,
            value = 10.0,
            createdAt = LocalDateTime.now(),
            paidAt = null,
        )
    }
}