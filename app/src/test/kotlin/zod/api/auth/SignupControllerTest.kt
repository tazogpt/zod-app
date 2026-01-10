package zod.api.auth

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.mockito.Mockito.times
import org.springframework.http.HttpStatus
import zod.member.application.command.SignupCommandService
import zod.member.application.dto.AuthDto

class SignupControllerTest {

    @Test
    fun `회원가입 요청은 성공 메시지를 반환한다`() {
        val signupService = Mockito.mock(SignupCommandService::class.java)
        val controller = SignupController(signupService)

        val response = controller.signup(AuthDto.SignupRequest("user-1", "nick", "pw1234"))

        assertEquals(HttpStatus.OK, response.statusCode)
        val body = response.body
        assertNotNull(body)
        assertEquals("SUCCESS", body?.code)
        assertEquals("가입 신청이 완료되었습니다.", body?.message)
        Mockito.verify(signupService, times(1)).signup("user-1", "nick", "pw1234")
    }
}
