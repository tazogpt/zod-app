package zod.common.security

import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.servlet.HandlerExceptionResolver
import org.springframework.web.filter.OncePerRequestFilter
import zod.common.error.exception.ApiException

class JwtAuthenticationFilter(
    private val tokenProvider: JwtTokenProvider,
    private val handlerExceptionResolver: HandlerExceptionResolver,
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain,
    ) {
        try {
            val token = resolveToken(request)
            if (!token.isNullOrBlank()) {
                val authentication = tokenProvider.getAuthentication(token)
                SecurityContextHolder.getContext().authentication = authentication
            }
            filterChain.doFilter(request, response)
        } catch (ex: ApiException) {
            SecurityContextHolder.clearContext()
            handlerExceptionResolver.resolveException(request, response, null, ex)
        }
    }

    private fun resolveToken(request: HttpServletRequest): String? {
        val header = request.getHeader(ACCESS_HEADER) ?: return null
        return if (header.startsWith(BEARER_PREFIX)) {
            header.substring(BEARER_PREFIX.length).trim()
        } else {
            null
        }
    }

    companion object {
        private const val ACCESS_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}
