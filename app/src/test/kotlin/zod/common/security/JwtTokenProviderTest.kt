package zod.common.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import java.time.Instant
import java.util.Base64
import java.util.Date

class JwtTokenProviderTest {
    private val secretBase64 = Base64.getEncoder().encodeToString(ByteArray(64) { 1 })
    private val provider = JwtTokenProvider(
        secret = secretBase64,
        accessMinutes = 30,
        refreshMinutes = 60,
    )

    @Test
    fun `access 토큰은 생성 후 claims를 정상 파싱한다`() {
        val token = provider.createAccessToken(
            userid = "user-1",
            nickname = "nick",
            role = "ADMIN",
            level = 3,
        )

        val claims = provider.parseClaims(token)

        assertEquals("user-1", claims.subject)
        assertEquals("nick", claims["nickname"])
        assertEquals("ADMIN", claims["role"])
        assertEquals(3, claims["level"])
    }

    @Test
    fun `만료된 토큰은 TOKEN_EXPIRED 예외를 반환한다`() {
        val expiredToken = createToken(
            secret = secretBase64,
            subject = "user-1",
            issuedAt = Instant.now().minusSeconds(120),
            expiresAt = Instant.now().minusSeconds(60),
        )

        val ex = assertThrows(ApiException::class.java) {
            provider.parseClaims(expiredToken)
        }

        assertEquals(ErrorCode.TOKEN_EXPIRED, ex.errorCode)
    }

    @Test
    fun `잘못된 토큰은 TOKEN_INVALID 예외를 반환한다`() {
        val otherSecret = Base64.getEncoder().encodeToString(ByteArray(64) { 2 })
        val invalidToken = createToken(
            secret = otherSecret,
            subject = "user-1",
            issuedAt = Instant.now().minusSeconds(60),
            expiresAt = Instant.now().plusSeconds(600),
        )

        val ex = assertThrows(ApiException::class.java) {
            provider.parseClaims(invalidToken)
        }

        assertEquals(ErrorCode.TOKEN_INVALID, ex.errorCode)
    }

    private fun createToken(
        secret: String,
        subject: String,
        issuedAt: Instant,
        expiresAt: Instant,
    ): String {
        val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
        return Jwts.builder()
            .subject(subject)
            .issuedAt(Date.from(issuedAt))
            .expiration(Date.from(expiresAt))
            .signWith(key, Jwts.SIG.HS512)
            .compact()
    }
}
