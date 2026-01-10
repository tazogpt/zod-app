package zod.member.infra.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus
import java.time.LocalDateTime

class MemberEntityTest {

    @Test
    fun `MemberEntity는 도메인 객체로 변환한다`() {
        val signupAt = LocalDateTime.now()
        val entity = MemberEntity(
            id = 1L,
            userid = "user-1",
            nickname = "nick",
            password = "pw",
            role = MemberRole.USER,
            status = MemberStatus.ACTIVE,
            level = 2,
            signupAt = signupAt,
        )

        val domain = entity.toDomain()

        assertEquals("user-1", domain.userid)
        assertEquals("nick", domain.nickname)
        assertEquals("pw", domain.password)
        assertEquals(MemberRole.USER, domain.role)
        assertEquals(MemberStatus.ACTIVE, domain.status)
        assertEquals(2, domain.level)
        assertEquals(signupAt, domain.signupAt)
    }
}
