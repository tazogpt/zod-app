package zod.api.auth

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.springframework.http.HttpStatus
import zod.member.application.command.AuthCommandService
import zod.member.application.dto.AuthDto

class AuthControllerTest {

    @Test
    fun `로그인 요청은 토큰 응답을 반환한다`() {
        val service = Mockito.mock(AuthCommandService::class.java)
        val controller = AuthController(service)
        val result = AuthDto.ResultTokens("access-token", "refresh-token")
        Mockito.`when`(service.login("user-1", "pw1234")).thenReturn(result)

        val response = controller.login(AuthDto.LoginRequest("user-1", "pw1234"))

        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertNotNull(body)
        assertEquals("SUCCESS", body?.code)
        assertEquals("access-token", body?.data?.accessToken)
        assertEquals("refresh-token", body?.data?.refreshToken)
        Mockito.verify(service, times(1)).login("user-1", "pw1234")
    }

    @Test
    fun `리프레시 요청은 토큰 응답을 반환한다`() {
        val service = Mockito.mock(AuthCommandService::class.java)
        val controller = AuthController(service)
        val result = AuthDto.ResultTokens("new-access", "new-refresh")
        Mockito.`when`(service.refresh("refresh-token")).thenReturn(result)

        val response = controller.refresh(AuthDto.RefreshRequest("refresh-token"))

        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertNotNull(body)
        assertEquals("SUCCESS", body?.code)
        assertEquals("new-access", body?.data?.accessToken)
        assertEquals("new-refresh", body?.data?.refreshToken)
        Mockito.verify(service, times(1)).refresh("refresh-token")
    }

    @Test
    fun `로그아웃 요청은 성공 응답을 반환한다`() {
        val service = Mockito.mock(AuthCommandService::class.java)
        val controller = AuthController(service)

        val response = controller.logout(AuthDto.LogoutRequest("user-1", "refresh-token"))

        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertNotNull(body)
        assertEquals("SUCCESS", body?.code)
        Mockito.verify(service, times(1)).logout("user-1", "refresh-token")
    }
}
