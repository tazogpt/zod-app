package zod.common.response

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus

class ApiResponseTest {

    @Test
    fun `성공 응답은 기본 코드를 가진다`() {
        val response = ApiResponse.success()

        assertEquals("SUCCESS", response.code)
        assertEquals("", response.message)
    }

    @Test
    fun `성공 응답은 데이터와 함께 생성된다`() {
        val response = ApiResponse.success("payload")

        assertEquals("payload", response.data)
        assertEquals("SUCCESS", response.code)
    }

    @Test
    fun `에러 응답은 코드와 메시지를 포함한다`() {
        val response = ApiResponse.error("ERROR", "message")

        assertEquals("ERROR", response.code)
        assertEquals("message", response.message)
    }

    @Test
    fun `ResponseEntity 변환은 상태 코드를 반영한다`() {
        val response = ApiResponse.success("ok")

        val entity = response.toResponseEntity(HttpStatus.CREATED)

        assertEquals(HttpStatus.CREATED, entity.statusCode)
        assertNotNull(entity.body)
        assertEquals("ok", entity.body?.data)
    }
}
