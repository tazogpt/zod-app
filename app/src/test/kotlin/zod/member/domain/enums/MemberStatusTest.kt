package zod.member.domain.enums

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MemberStatusTest {

    @Test
    fun `ACTIVE 상태는 정상 라벨을 가진다`() {
        assertEquals("정상", MemberStatus.ACTIVE.label)
    }

    @Test
    fun `BLOCK 상태는 로그인금지 라벨을 가진다`() {
        assertEquals("로그인금지", MemberStatus.BLOCK.label)
    }
}
