package zod.payment.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import zod.payment.domain.enums.BankingStatus
import zod.payment.domain.enums.BankingType
import zod.wallet.domain.model.Money
import zod.wallet.domain.model.Point

class BankingTest {

    @Test
    fun `REQUEST 상태에서 approve를 호출하면 APPROVE로 전이된다`() {
        val requestedAt = LocalDateTime.now()
        val processedAt = requestedAt.plusMinutes(1)
        val banking = Banking(
            userid = "user-1",
            type = BankingType.DEPOSIT,
            amount = Money(1000),
            bonusPoint = Point(100),
            status = BankingStatus.REQUEST,
            requestedAt = requestedAt,
        )

        banking.approve(processedBy = "admin", processedAt = processedAt)

        assertEquals(BankingStatus.APPROVE, banking.status)
        assertEquals("admin", banking.processedBy)
        assertEquals(processedAt, banking.processedAt)
    }

    @Test
    fun `REQUEST 상태에서 cancel을 호출하면 CANCEL로 전이된다`() {
        val requestedAt = LocalDateTime.now()
        val processedAt = requestedAt.plusMinutes(1)
        val banking = Banking(
            userid = "user-1",
            type = BankingType.WITHDRAW,
            amount = Money(500),
            bonusPoint = Point(0),
            status = BankingStatus.REQUEST,
            requestedAt = requestedAt,
        )

        banking.cancel(processedBy = "admin", processedAt = processedAt)

        assertEquals(BankingStatus.CANCEL, banking.status)
        assertEquals("admin", banking.processedBy)
        assertEquals(processedAt, banking.processedAt)
    }

    @Test
    fun `APPROVE 상태에서 rollback을 호출하면 ROLLBACK으로 전이된다`() {
        val requestedAt = LocalDateTime.now()
        val processedAt = requestedAt.plusMinutes(1)
        val rollbackAt = processedAt.plusMinutes(1)
        val banking = Banking(
            userid = "user-1",
            type = BankingType.DEPOSIT,
            amount = Money(1000),
            bonusPoint = Point(100),
            status = BankingStatus.REQUEST,
            requestedAt = requestedAt,
        )

        banking.approve(processedBy = "admin", processedAt = processedAt)
        banking.rollback(processedBy = "admin", processedAt = rollbackAt)

        assertEquals(BankingStatus.ROLLBACK, banking.status)
        assertEquals("admin", banking.processedBy)
        assertEquals(rollbackAt, banking.processedAt)
    }

    @Test
    fun `REQUEST 상태에서 rollback을 호출하면 예외가 발생한다`() {
        val banking = Banking(
            userid = "user-1",
            type = BankingType.WITHDRAW,
            amount = Money(500),
            bonusPoint = Point(0),
            status = BankingStatus.REQUEST,
            requestedAt = LocalDateTime.now(),
        )

        assertThrows(IllegalStateException::class.java) {
            banking.rollback(processedBy = "admin", processedAt = LocalDateTime.now())
        }
    }

    @Test
    fun `CANCEL 상태에서는 approve를 호출할 수 없다`() {
        val banking = Banking(
            userid = "user-1",
            type = BankingType.WITHDRAW,
            amount = Money(500),
            bonusPoint = Point(0),
            status = BankingStatus.REQUEST,
            requestedAt = LocalDateTime.now(),
        )

        banking.cancel(processedBy = "admin", processedAt = LocalDateTime.now())

        assertThrows(IllegalStateException::class.java) {
            banking.approve(processedBy = "admin", processedAt = LocalDateTime.now())
        }
    }
}
