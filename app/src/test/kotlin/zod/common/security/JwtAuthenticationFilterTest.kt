package zod.common.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import jakarta.servlet.FilterChain
import jakarta.servlet.ServletRequest
import jakarta.servlet.ServletResponse
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.mock.web.MockHttpServletRequest
import org.springframework.mock.web.MockHttpServletResponse
import org.springframework.web.servlet.HandlerExceptionResolver
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import java.time.Instant
import java.util.Base64
import java.util.Date

class JwtAuthenticationFilterTest {
    private val secretBase64 = Base64.getEncoder().encodeToString(ByteArray(64) { 7 })
    private val provider = JwtTokenProvider(
        secret = secretBase64,
        accessMinutes = 30,
        refreshMinutes = 60,
    )

    @Test
    fun `Authorization 헤더가 없으면 체인을 통과한다`() {
        val resolver = CapturingExceptionResolver()
        val chain = CapturingFilterChain()
        val filter = JwtAuthenticationFilter(provider, resolver)
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()

        filter.doFilter(request, response, chain)

        assertTrue(chain.called)
        assertNull(resolver.exception)
    }

    @Test
    fun `잘못된 토큰은 예외 처리로 전달한다`() {
        val resolver = CapturingExceptionResolver()
        val chain = CapturingFilterChain()
        val filter = JwtAuthenticationFilter(provider, resolver)
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        request.addHeader("Authorization", "Bearer invalid-token")

        filter.doFilter(request, response, chain)

        assertFalse(chain.called)
        val ex = resolver.exception as ApiException
        assertEquals(ErrorCode.TOKEN_INVALID, ex.errorCode)
    }

    @Test
    fun `swagger 경로는 필터를 건너뛴다`() {
        val resolver = CapturingExceptionResolver()
        val chain = CapturingFilterChain()
        val filter = JwtAuthenticationFilter(provider, resolver)
        val request = MockHttpServletRequest()
        val response = MockHttpServletResponse()
        request.servletPath = "/swagger-ui/index.html"
        request.addHeader("Authorization", "Bearer ${createToken()}")

        filter.doFilter(request, response, chain)

        assertTrue(chain.called)
        assertNull(resolver.exception)
    }

    private fun createToken(): String {
        val key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretBase64))
        val now = Instant.now()
        val expiry = now.plusSeconds(600)
        return Jwts.builder()
            .subject("user-1")
            .issuedAt(Date.from(now))
            .expiration(Date.from(expiry))
            .signWith(key, Jwts.SIG.HS512)
            .compact()
    }

    private class CapturingFilterChain : FilterChain {
        var called = false

        override fun doFilter(request: ServletRequest, response: ServletResponse) {
            called = true
        }
    }

    private class CapturingExceptionResolver : HandlerExceptionResolver {
        var exception: Exception? = null

        override fun resolveException(
            request: jakarta.servlet.http.HttpServletRequest,
            response: jakarta.servlet.http.HttpServletResponse,
            handler: Any?,
            ex: Exception,
        ): org.springframework.web.servlet.ModelAndView? {
            exception = ex
            return null
        }
    }
}
