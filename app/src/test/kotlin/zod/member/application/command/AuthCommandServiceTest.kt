package zod.member.application.command

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import zod.common.security.JwtTokenProvider
import zod.member.application.port.MemberQueryPort
import zod.member.application.port.TokenCommandPort
import zod.member.application.port.TokenQueryPort
import zod.member.application.query.AuthQueryService
import zod.member.application.query.model.AuthUser
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus
import java.util.Base64

class AuthCommandServiceTest {
    private val secretBase64 = Base64.getEncoder().encodeToString(ByteArray(64) { 3 })
    private val tokenProvider = JwtTokenProvider(
        secret = secretBase64,
        accessMinutes = 30,
        refreshMinutes = 60,
    )
    private val passwordEncoder = BCryptPasswordEncoder()

    @Test
    fun `로그인 성공 시 토큰을 반환하고 저장한다`() {
        val member = createAuthUser(encodePassword("pw1234"))
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)

        val result = authService.login("user-1", "pw1234")

        val storedRefresh = requireNotNull(tokenStore.findRefreshTokenByUserid("user-1"))
        assertEquals(result.refreshToken, storedRefresh)
        assertEquals("user-1", tokenProvider.parseClaims(result.accessToken).subject)
    }

    @Test
    fun `잘못된 비밀번호는 UNAUTHORIZED 예외를 반환한다`() {
        val member = createAuthUser(encodePassword("pw1234"))
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)

        val ex = assertThrows(ApiException::class.java) {
            authService.login("user-1", "wrong")
        }

        assertEquals(ErrorCode.UNAUTHORIZED, ex.errorCode)
    }

    @Test
    fun `비활성 사용자는 UNAUTHORIZED 예외를 반환한다`() {
        val member = createAuthUser(encodePassword("pw1234"), MemberStatus.BLOCK)
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)

        val ex = assertThrows(ApiException::class.java) {
            authService.login("user-1", "pw1234")
        }

        assertEquals(ErrorCode.UNAUTHORIZED, ex.errorCode)
    }

    @Test
    fun `refresh 토큰 검증 후 새 토큰을 저장한다`() {
        val member = createAuthUser(encodePassword("pw1234"))
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)
        val refreshToken = tokenProvider.createRefreshToken("user-1")
        tokenStore.save("user-1", "old-access", refreshToken)

        val result = authService.refresh(refreshToken)

        val storedRefresh = requireNotNull(tokenStore.findRefreshTokenByUserid("user-1"))
        assertEquals(storedRefresh, result.refreshToken)
    }

    @Test
    fun `logout 시 저장된 토큰과 일치하면 삭제한다`() {
        val member = createAuthUser(encodePassword("pw1234"))
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)
        val refreshToken = tokenProvider.createRefreshToken("user-1")
        tokenStore.save("user-1", "access-token", refreshToken)

        authService.logout(refreshToken)

        assertNull(tokenStore.findRefreshTokenByUserid("user-1"))
    }

    @Test
    fun `logout 시 저장된 토큰과 불일치하면 삭제하지 않는다`() {
        val member = createAuthUser(encodePassword("pw1234"))
        val tokenStore = InMemoryTokenStore()
        val authService = createService(member, tokenStore, tokenStore)
        val storedRefreshToken = tokenProvider.createRefreshToken("user-1")
        val differentRefreshToken = tokenProvider.createRefreshToken("user-2")
        tokenStore.save("user-1", "access-token", storedRefreshToken)

        authService.logout(differentRefreshToken)

        assertEquals(storedRefreshToken, tokenStore.findRefreshTokenByUserid("user-1"))
    }

    private fun createService(
        member: AuthUser,
        commandPort: TokenCommandPort,
        queryPort: TokenQueryPort,
    ): AuthCommandService {
        val authQueryService = AuthQueryService(
            memberQueryPort = InMemoryMemberPort(member),
            tokenQueryPort = queryPort,
        )
        return AuthCommandService(
            authQueryService = authQueryService,
            tokenCommandPort = commandPort,
            passwordEncoder = passwordEncoder,
            jwtTokenProvider = tokenProvider,
        )
    }

    private fun createAuthUser(
        password: String,
        status: MemberStatus = MemberStatus.ACTIVE,
    ): AuthUser {
        return AuthUser(
            userid = "user-1",
            nickname = "nick",
            password = password,
            role = MemberRole.USER,
            status = status,
            level = 1,
        )
    }

    private fun encodePassword(raw: String): String {
        return requireNotNull(passwordEncoder.encode(raw))
    }

    private class InMemoryMemberPort(
        private val member: AuthUser,
    ) : MemberQueryPort {
        override fun findAuthUserByUserid(userid: String): AuthUser? {
            return if (userid == member.userid) member else null
        }
    }

    private class InMemoryTokenStore : TokenCommandPort, TokenQueryPort {
        private val tokensByUser = mutableMapOf<String, String>()

        override fun save(userid: String, accessToken: String, refreshToken: String) {
            tokensByUser[userid] = refreshToken
        }

        override fun deleteByUserid(userid: String) {
            tokensByUser.remove(userid)
        }

        override fun findRefreshTokenByUserid(userid: String): String? {
            return tokensByUser[userid]
        }
    }
}
