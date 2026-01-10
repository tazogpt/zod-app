package zod.member.application.port

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class TokenQueryPortTest {

    @Test
    fun `TokenQueryPort는 리프레시 토큰 조회 메서드를 가진다`() {
        val method = TokenQueryPort::class.java.methods
            .firstOrNull { it.name == "findRefreshTokenByUserid" }

        assertNotNull(method)
        assertEquals(String::class.java, method?.returnType)
    }
}
