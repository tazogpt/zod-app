package zod.common.error

import org.springframework.http.HttpStatus

enum class ErrorCode(
    val status: HttpStatus,
    val message: String,
) {
    UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "Invalid credentials"),
    TOKEN_EXPIRED(HttpStatus.UNAUTHORIZED, "Token expired"),
    TOKEN_INVALID(HttpStatus.UNAUTHORIZED, "Invalid token"),
}
