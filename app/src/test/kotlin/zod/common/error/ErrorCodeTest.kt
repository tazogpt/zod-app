package zod.common.error

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ErrorCodeTest {

    @Test
    fun `UNAUTHORIZED 코드는 상태와 메시지를 가진다`() {
        assertEquals(HttpStatus.UNAUTHORIZED, ErrorCode.UNAUTHORIZED.status)
        assertEquals("Invalid credentials", ErrorCode.UNAUTHORIZED.message)
    }

    @Test
    fun `TOKEN_INVALID 코드는 상태와 메시지를 가진다`() {
        assertEquals(HttpStatus.UNAUTHORIZED, ErrorCode.TOKEN_INVALID.status)
        assertEquals("Invalid token", ErrorCode.TOKEN_INVALID.message)
    }
}
