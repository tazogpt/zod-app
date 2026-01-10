package zod.wallet.domain.enums

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class MoneyCodeTest {

    @Test
    fun `fromValue는 대소문자를 무시하고 매핑한다`() {
        val code = MoneyCode.fromValue("deposit")

        assertEquals(MoneyCode.DEPOSIT, code)
    }

    @Test
    fun `rollback은 입출금에 대한 취소 코드를 반환한다`() {
        assertEquals(MoneyCode.DEPOSIT_CANCEL, MoneyCode.DEPOSIT.rollback())
        assertEquals(MoneyCode.WITHDRAW_CANCEL, MoneyCode.WITHDRAW.rollback())
    }

    @Test
    fun `fromValue는 알 수 없는 값이면 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            MoneyCode.fromValue("unknown")
        }
    }
}
