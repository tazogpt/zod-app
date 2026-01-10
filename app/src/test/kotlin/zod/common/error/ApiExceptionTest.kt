package zod.common.error

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertSame
import org.junit.jupiter.api.Test
import zod.common.error.exception.ApiException

class ApiExceptionTest {

    @Test
    fun `ApiException은 에러코드와 원인을 보존한다`() {
        val cause = IllegalStateException("cause")

        val ex = ApiException(ErrorCode.UNAUTHORIZED, cause)

        assertEquals(ErrorCode.UNAUTHORIZED, ex.errorCode)
        assertEquals(ErrorCode.UNAUTHORIZED.message, ex.message)
        assertSame(cause, ex.cause)
    }
}
