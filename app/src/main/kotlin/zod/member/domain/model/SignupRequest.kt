package zod.member.domain.model

import java.time.LocalDateTime

data class SignupRequest(
    val userid: String,
    val nickname: String,
    val password: String,
    val requestedAt: LocalDateTime,
)
