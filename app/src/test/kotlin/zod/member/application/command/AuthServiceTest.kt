package zod.member.application.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import zod.common.security.JwtTokenProvider
import zod.member.application.dto.MemberDto
import zod.member.domain.port.MemberQueryPort
import zod.member.domain.port.TokenCommandPort
import zod.member.domain.port.TokenQueryPort
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus
import zod.member.domain.model.Member
import java.util.Base64

class AuthServiceTest {
    private val secretBase64 = Base64.getEncoder().encodeToString(ByteArray(64) { 3 })
    private val tokenProvider = JwtTokenProvider(
        secret = secretBase64,
        accessMinutes = 30,
        refreshMinutes = 60,
    )
    private val passwordEncoder = BCryptPasswordEncoder()

    @Test
    fun `로그인 성공 시 토큰을 반환하고 저장한다`() {
        val member = createMember(requireNotNull(passwordEncoder.encode("pw1234")))
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)

        val result = authService.login("user-1", "pw1234")

        val storedRefresh = requireNotNull(tokenStore.findRefreshTokenByUserid("user-1"))
        assertEquals(result.refreshToken, storedRefresh)
        assertEquals("user-1", tokenProvider.parseClaims(result.accessToken).subject)
    }

    @Test
    fun `잘못된 비밀번호는 UNAUTHORIZED 예외를 반환한다`() {
        val member = createMember(requireNotNull(passwordEncoder.encode("pw1234")))
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)

        val ex = assertThrows(ApiException::class.java) {
            authService.login("user-1", "wrong")
        }

        assertEquals(ErrorCode.UNAUTHORIZED, ex.errorCode)
    }

    @Test
    fun `refresh 토큰 검증 후 새 토큰을 저장한다`() {
        val member = createMember(requireNotNull(passwordEncoder.encode("pw1234")))
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)
        val refreshToken = tokenProvider.createRefreshToken("user-1")
        tokenStore.save("user-1", "old-access", refreshToken)

        val result = authService.refresh(refreshToken)

        val storedRefresh = requireNotNull(tokenStore.findRefreshTokenByUserid("user-1"))
        assertEquals(storedRefresh, result.refreshToken)
    }

    private fun createService(
        member: MemberDto.LoginUser,
        commandRepository: TokenCommandPort,
        queryRepository: TokenQueryPort,
    ): AuthService {
        return AuthService(
            memberQueryPort = InMemoryMemberPort(member),
            tokenCommandPort = commandRepository,
            tokenQueryPort = queryRepository,
            passwordEncoder = passwordEncoder,
            jwtTokenProvider = tokenProvider,
        )
    }

    private fun createMember(password: String): MemberDto.LoginUser {
        return MemberDto.LoginUser(
            userid = "user-1",
            nickname = "nick",
            password = password,
            role = MemberRole.USER,
            status = MemberStatus.ACTIVE,
            level = 1,
        )
    }

    private class InMemoryMemberPort(
        private val member: MemberDto.LoginUser,
    ) : MemberQueryPort {
        override fun findLoginUserByUserid(userid: String): MemberDto.LoginUser? {
            return if (userid == member.userid) member else null
        }
    }

    private class InMemoryTokenStore : TokenCommandPort, TokenQueryPort {
        private val tokensByUser = mutableMapOf<String, String>()

        override fun save(userid: String, accessToken: String, refreshToken: String) {
            tokensByUser[userid] = refreshToken
        }

        override fun findRefreshTokenByUserid(userid: String): String? {
            return tokensByUser[userid]
        }
    }
}
