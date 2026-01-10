package zod.wallet.domain.enums

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test

class PointCodeTest {

    @Test
    fun `fromValue는 대소문자를 무시하고 매핑한다`() {
        val code = PointCode.fromValue("deposit")

        assertEquals(PointCode.DEPOSIT, code)
    }

    @Test
    fun `rollback은 포인트 취소 코드를 반환한다`() {
        assertEquals(PointCode.DEPOSIT_CANCEL, PointCode.DEPOSIT.rollback())
        assertEquals(PointCode.LOSE_CANCEL, PointCode.LOSE.rollback())
    }

    @Test
    fun `fromValue는 알 수 없는 값이면 예외를 던진다`() {
        assertThrows(IllegalArgumentException::class.java) {
            PointCode.fromValue("unknown")
        }
    }
}
