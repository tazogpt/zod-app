package zod.member.infra.entity

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import java.time.LocalDateTime

class TokenEntityTest {

    @Test
    fun `TokenEntity는 입력 값을 보존한다`() {
        val now = LocalDateTime.now()
        val entity = TokenEntity(
            userid = "user-1",
            accessToken = "access",
            refreshToken = "refresh",
            updatedAt = now,
        )

        assertEquals("user-1", entity.userid)
        assertEquals("access", entity.accessToken)
        assertEquals("refresh", entity.refreshToken)
        assertEquals(now, entity.updatedAt)
    }
}
