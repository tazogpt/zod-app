package zod.member.application.query.model

import zod.common.annotation.NoArgs
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus

@NoArgs
data class AuthUser(
    val userid: String,
    val nickname: String,
    val password: String,
    val role: MemberRole,
    val status: MemberStatus,
    val level: Int,
)
