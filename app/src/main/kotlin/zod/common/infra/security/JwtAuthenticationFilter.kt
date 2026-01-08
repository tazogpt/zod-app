package zod.common.infra.security

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import zod.common.api.error.exception.ApiException
import zod.common.api.response.ApiResponse

class JwtAuthenticationFilter(
    private val tokenProvider: JwtTokenProvider,
    private val objectMapper: ObjectMapper,
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
            writeErrorResponse(response, ex)
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

    private fun writeErrorResponse(response: HttpServletResponse, ex: ApiException) {
        val errorCode = ex.errorCode
        response.status = errorCode.status.value()
        response.contentType = "application/json"
        response.characterEncoding = "UTF-8"
        val body = ApiResponse.error(errorCode.name, errorCode.message)
        response.writer.write(objectMapper.writeValueAsString(body))
    }

    companion object {
        private const val ACCESS_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}
