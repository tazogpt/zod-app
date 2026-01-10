package zod.member.domain.model

import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus
import java.time.LocalDateTime

data class Member(
    val userid: String,
    val nickname: String,
    val password: String,
    val role: MemberRole,
    val status: MemberStatus,
    val level: Int,
    val signupAt: LocalDateTime?,
)
