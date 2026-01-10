package zod.common.error

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ErrorCodeTest {

    @Test
    fun `UNAUTHORIZED 코드는 상태와 메시지를 가진다`() {
        assertEquals(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED.status)
        assertEquals("인증 정보가 올바르지 않습니다.", ErrorCode.UNAUTHORIZED.message)
    }

    @Test
    fun `TOKEN_INVALID 코드는 상태와 메시지를 가진다`() {
        assertEquals(HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_INVALID.status)
        assertEquals("유효하지 않은 토큰입니다.", ErrorCode.TOKEN_INVALID.message)
    }
}
