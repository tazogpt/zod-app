package zod.member.application.query.model

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus

class AuthUserTest {

    @Test
    fun `AuthUser는 입력 값을 그대로 가진다`() {
        val user = AuthUser(
            userid = "user-1",
            nickname = "nick",
            password = "encoded",
            role = MemberRole.USER,
            status = MemberStatus.ACTIVE,
            level = 2,
        )

        assertEquals("user-1", user.userid)
        assertEquals("nick", user.nickname)
        assertEquals("encoded", user.password)
        assertEquals(MemberRole.USER, user.role)
        assertEquals(MemberStatus.ACTIVE, user.status)
        assertEquals(2, user.level)
    }
}
