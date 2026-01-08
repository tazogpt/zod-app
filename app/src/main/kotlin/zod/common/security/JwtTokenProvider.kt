package zod.common.security

import io.jsonwebtoken.Claims
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.JwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import java.util.Date

@Component
class JwtTokenProvider(
    @Value("\${security.jwt.secret}") private val secret: String,
    @Value("\${security.jwt.access-mins}") private val accessMinutes: Long,
    @Value("\${security.jwt.refresh-mins}") private val refreshMinutes: Long,
) {
    private val secretKey = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secret))
    private val accessSeconds: Long = accessMinutes * 60
    private val refreshSeconds: Long = refreshMinutes * 60

    fun createAccessToken(
        userid: String,
        nickname: String,
        role: String,
        level: Int,
    ): String {
        return createAccessToken(userid, nickname, role, level, accessSeconds)
    }

    fun createRefreshToken(
        userid: String,
    ): String {
        return createRefreshToken(userid, refreshSeconds)
    }

    fun getAuthentication(token: String): Authentication {
        val claims = parseClaims(token)
        val role = claims["role"]?.toString() ?: "USER"
        val authority = SimpleGrantedAuthority(normalizeRole(role))
        return UsernamePasswordAuthenticationToken(claims.subject, null, listOf(authority))
    }

    fun parseClaims(token: String): Claims {
        try {
            return Jwts
                .parser()
                .verifyWith(secretKey)
                .build()
                .parseSignedClaims(token)
                .payload
        } catch (ex: ExpiredJwtException) {
            throw ApiException(ErrorCode.TOKEN_EXPIRED, ex)
        } catch (ex: JwtException) {
            throw ApiException(ErrorCode.TOKEN_INVALID, ex)
        } catch (ex: IllegalArgumentException) {
            throw ApiException(ErrorCode.TOKEN_INVALID, ex)
        }
    }

    private fun createAccessToken(
        userid: String,
        nickname: String,
        role: String,
        level: Int,
        validitySeconds: Long,
    ): String {
        val now = Date()
        val expiry = Date(now.time + validitySeconds * 1000)
        return Jwts.builder()
            .subject(userid)
            .claim("nickname", nickname)
            .claim("role", role)
            .claim("level", level)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(secretKey, Jwts.SIG.HS512)
            .compact()
    }

    private fun createRefreshToken(
        userid: String,
        validitySeconds: Long,
    ): String {
        val now = Date()
        val expiry = Date(now.time + validitySeconds * 1000)
        return Jwts.builder()
            .subject(userid)
            .issuedAt(now)
            .expiration(expiry)
            .signWith(secretKey, Jwts.SIG.HS512)
            .compact()
    }

    private fun normalizeRole(role: String): String {
        return if (role.startsWith("ROLE_")) role else "ROLE_$role"
    }
}
