package zod.member.application.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import zod.member.application.port.MemberQueryPort
import zod.member.application.port.SignupCommandPort
import zod.member.application.query.model.AuthUser
import zod.member.domain.model.SignupRequest

class SignupCommandServiceTest {

    private val passwordEncoder = BCryptPasswordEncoder()

    @Test
    fun `회원가입 신청 시 저장한다`() {
        val memberQueryPort = InMemoryMemberQueryPort()
        val signupCommandPort = InMemorySignupCommandPort()
        val service = createService(memberQueryPort, signupCommandPort)

        service.signup("user-1", "nick", "pw1234")

        val saved = requireNotNull(signupCommandPort.saved)
        assertEquals("user-1", saved.userid)
        assertEquals("nick", saved.nickname)
        assertTrue(passwordEncoder.matches("pw1234", saved.password))
    }

    @Test
    fun `userid 중복이면 CONFLICT 예외를 반환한다`() {
        val memberQueryPort = InMemoryMemberQueryPort(existingUserid = "user-1")
        val signupCommandPort = InMemorySignupCommandPort()
        val service = createService(memberQueryPort, signupCommandPort)

        val ex = assertThrows(ApiException::class.java) {
            service.signup("user-1", "nick", "pw1234")
        }

        assertEquals(ErrorCode.DUPLICATE_USERID, ex.errorCode)
    }

    @Test
    fun `nickname 중복이면 CONFLICT 예외를 반환한다`() {
        val memberQueryPort = InMemoryMemberQueryPort(existingNickname = "nick")
        val signupCommandPort = InMemorySignupCommandPort()
        val service = createService(memberQueryPort, signupCommandPort)

        val ex = assertThrows(ApiException::class.java) {
            service.signup("user-1", "nick", "pw1234")
        }

        assertEquals(ErrorCode.DUPLICATE_NICKNAME, ex.errorCode)
    }

    @Test
    fun `비밀번호가 4자 미만이면 BAD_REQUEST 예외를 반환한다`() {
        val memberQueryPort = InMemoryMemberQueryPort()
        val signupCommandPort = InMemorySignupCommandPort()
        val service = createService(memberQueryPort, signupCommandPort)

        val ex = assertThrows(ApiException::class.java) {
            service.signup("user-1", "nick", "pw1")
        }

        assertEquals(ErrorCode.INVALID_PASSWORD, ex.errorCode)
    }

    private fun createService(
        memberQueryPort: MemberQueryPort,
        signupCommandPort: SignupCommandPort,
    ): SignupCommandService {
        return SignupCommandService(
            memberQueryPort = memberQueryPort,
            signupCommandPort = signupCommandPort,
            passwordEncoder = passwordEncoder,
        )
    }

    private class InMemoryMemberQueryPort(
        private val existingUserid: String? = null,
        private val existingNickname: String? = null,
    ) : MemberQueryPort {
        override fun findAuthUserByUserid(userid: String): AuthUser? = null

        override fun existsByUserid(userid: String): Boolean {
            return existingUserid == userid
        }

        override fun existsByNickname(nickname: String): Boolean {
            return existingNickname == nickname
        }
    }

    private class InMemorySignupCommandPort : SignupCommandPort {
        var saved: SignupRequest? = null

        override fun save(request: SignupRequest) {
            saved = request
        }
    }

}
