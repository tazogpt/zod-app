package zod.member.application.query

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import zod.member.application.port.MemberQueryPort
import zod.member.application.port.TokenQueryPort
import zod.member.application.query.model.AuthUser
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus

class AuthQueryServiceTest {

    @Test
    fun `findLoginUserByUserid는 사용자를 반환한다`() {
        val member = createAuthUser()
        val service = createService(member, null)

        val result = service.findLoginUserByUserid("user-1")

        assertEquals(member, result)
    }

    @Test
    fun `findLoginUserByUserid는 존재하지 않는 사용자에 대해 null을 반환한다`() {
        val member = createAuthUser()
        val service = createService(member, null)

        val result = service.findLoginUserByUserid("unknown")

        assertNull(result)
    }

    @Test
    fun `findRefreshTokenByUserid는 토큰을 반환한다`() {
        val service = createService(null, "stored-refresh-token")

        val result = service.findRefreshTokenByUserid("user-1")

        assertEquals("stored-refresh-token", result)
    }

    @Test
    fun `findRefreshTokenByUserid는 토큰이 없으면 null을 반환한다`() {
        val service = createService(null, null)

        val result = service.findRefreshTokenByUserid("unknown")

        assertNull(result)
    }

    private fun createService(
        member: AuthUser?,
        refreshToken: String?,
    ): AuthQueryService {
        return AuthQueryService(
            memberQueryPort = InMemoryMemberPort(member),
            tokenQueryPort = InMemoryTokenQueryPort(refreshToken),
        )
    }

    private fun createAuthUser(): AuthUser {
        return AuthUser(
            userid = "user-1",
            nickname = "nick",
            password = "encoded-password",
            role = MemberRole.USER,
            status = MemberStatus.ACTIVE,
            level = 1,
        )
    }

    private class InMemoryMemberPort(
        private val member: AuthUser?,
    ) : MemberQueryPort {
        override fun findAuthUserByUserid(userid: String): AuthUser? {
            return if (member != null && userid == member.userid) member else null
        }

        override fun existsByUserid(userid: String): Boolean {
            return member != null && userid == member.userid
        }

        override fun existsByNickname(nickname: String): Boolean {
            return member != null && nickname == member.nickname
        }
    }

    private class InMemoryTokenQueryPort(
        private val refreshToken: String?,
    ) : TokenQueryPort {
        override fun findRefreshTokenByUserid(userid: String): String? {
            return if (userid == "user-1") refreshToken else null
        }
    }
}
