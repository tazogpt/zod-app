package zod.common.api.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Token expired"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Invalid token"),
}
