package zod.common.config

import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.web.servlet.HandlerExceptionResolver
import zod.common.security.JwtAuthenticationFilter
import zod.common.security.JwtTokenProvider

class SecConfigTest {

    @Test
    fun `JWT 필터 빈을 생성한다`() {
        val config = SecConfig()
        val provider = Mockito.mock(JwtTokenProvider::class.java)
        val resolver = Mockito.mock(HandlerExceptionResolver::class.java)

        val filter = config.jwtAuthenticationFilter(provider, resolver)

        assertNotNull(filter)
        assertTrue(filter is JwtAuthenticationFilter)
    }

    @Test
    fun `패스워드 인코더 빈은 BCrypt 구현이다`() {
        val config = SecConfig()

        val encoder: PasswordEncoder = config.passwordEncoder()

        assertTrue(encoder is BCryptPasswordEncoder)
    }

    @Test
    fun `보안 필터 체인을 생성한다`() {
        val config = SecConfig()
        val method = SecConfig::class.java.methods.first { it.name == "securityFilterChain" }

        assertNotNull(method)
    }
}
