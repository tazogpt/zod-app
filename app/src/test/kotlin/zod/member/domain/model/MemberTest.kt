package zod.member.domain.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus

class MemberTest {

    @Test
    fun `Member는 입력 값을 그대로 가진다`() {
        val member = Member(
            userid = "user-1",
            nickname = "nick",
            password = "pw",
            role = MemberRole.USER,
            status = MemberStatus.ACTIVE,
            level = 3,
        )

        assertEquals("user-1", member.userid)
        assertEquals("nick", member.nickname)
        assertEquals("pw", member.password)
        assertEquals(MemberRole.USER, member.role)
        assertEquals(MemberStatus.ACTIVE, member.status)
        assertEquals(3, member.level)
    }
}
