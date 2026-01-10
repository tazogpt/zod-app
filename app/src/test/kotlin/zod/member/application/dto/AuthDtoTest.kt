package zod.member.application.dto

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class AuthDtoTest {

    @Test
    fun `로그인 요청 DTO는 값을 보존한다`() {
        val request = AuthDto.LoginRequest("user-1", "pw1234")

        assertEquals("user-1", request.userid)
        assertEquals("pw1234", request.password)
    }

    @Test
    fun `리프레시 요청 DTO는 토큰 값을 보존한다`() {
        val request = AuthDto.RefreshRequest("refresh")

        assertEquals("refresh", request.refreshToken)
    }

    @Test
    fun `로그아웃 요청 DTO는 토큰 값을 보존한다`() {
        val request = AuthDto.LogoutRequest("refresh")

        assertEquals("refresh", request.refreshToken)
    }

    @Test
    fun `토큰 응답 DTO는 값을 보존한다`() {
        val response = AuthDto.TokenResponse("access", "refresh")

        assertEquals("access", response.accessToken)
        assertEquals("refresh", response.refreshToken)
    }
}
