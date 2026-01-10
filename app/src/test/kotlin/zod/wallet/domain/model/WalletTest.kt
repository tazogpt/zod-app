package zod.wallet.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime
import zod.wallet.domain.enums.MoneyCode
import zod.wallet.domain.enums.PointCode

class WalletTest {

    @Test
    fun `Wallet은 현재 머니와 포인트를 가진다`() {
        val wallet = Wallet(
            userid = "user-1",
            money = Money(1000),
            point = Point(200),
        )

        assertEquals("user-1", wallet.userid)
        assertEquals(1000, wallet.money.value)
        assertEquals(200, wallet.point.value)
    }

    @Test
    fun `MoneyEvent는 입력 값을 그대로 가진다`() {
        val createdAt = LocalDateTime.now()
        val event = MoneyEvent(
            userid = "user-1",
            type = MoneyCode.DEPOSIT,
            amount = Money(500),
            beforeBalance = Money(1000),
            refId = "banking-1",
            processedBy = "admin",
            createdAt = createdAt,
        )

        assertEquals("user-1", event.userid)
        assertEquals(MoneyCode.DEPOSIT, event.type)
        assertEquals(500, event.amount.value)
        assertEquals(1000, event.beforeBalance.value)
        assertEquals("banking-1", event.refId)
        assertEquals("admin", event.processedBy)
        assertEquals(createdAt, event.createdAt)
    }

    @Test
    fun `PointEvent는 입력 값을 그대로 가진다`() {
        val createdAt = LocalDateTime.now()
        val event = PointEvent(
            userid = "user-1",
            type = PointCode.DEPOSIT,
            amount = Point(50),
            beforeBalance = Point(200),
            refId = "banking-1",
            processedBy = "admin",
            createdAt = createdAt,
        )

        assertEquals("user-1", event.userid)
        assertEquals(PointCode.DEPOSIT, event.type)
        assertEquals(50, event.amount.value)
        assertEquals(200, event.beforeBalance.value)
        assertEquals("banking-1", event.refId)
        assertEquals("admin", event.processedBy)
        assertEquals(createdAt, event.createdAt)
    }
}
