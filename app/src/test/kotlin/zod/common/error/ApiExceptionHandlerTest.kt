package zod.common.error

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import zod.common.error.exception.ApiException

class ApiExceptionHandlerTest {

    private val handler = ApiExceptionHandler()

    @Test
    fun `ApiException은 에러코드로 응답한다`() {
        val response = handler.handleApiException(ApiException(ErrorCode.TOKEN_INVALID))

        assertEquals(HttpStatus.UNAUTHORIZED, response.statusCode)
        val body = response.body
        assertNotNull(body)
        assertEquals(ErrorCode.TOKEN_INVALID.name, body?.code)
        assertEquals(ErrorCode.TOKEN_INVALID.message, body?.message)
    }

    @Test
    fun `RuntimeException은 내부 오류로 응답한다`() {
        val response = handler.handleRuntimeException(RuntimeException("boom"))

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        val body = response.body
        assertNotNull(body)
        assertEquals("INTERNAL_ERROR", body?.code)
        assertEquals("boom", body?.message)
    }

    @Test
    fun `Exception은 내부 오류로 응답한다`() {
        val response = handler.handleException(Exception("fail"))

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.statusCode)
        val body = response.body
        assertNotNull(body)
        assertEquals("INTERNAL_ERROR", body?.code)
        assertEquals("fail", body?.message)
    }
}
