package zod.member.application.query

import org.springframework.stereotype.Service
import zod.member.application.port.MemberQueryPort
import zod.member.application.port.TokenQueryPort
import zod.member.application.query.model.AuthUser

@Service
class AuthQueryService(
    private val memberQueryPort: MemberQueryPort,
    private val tokenQueryPort: TokenQueryPort,
) {
    fun findLoginUserByUserid(userid: String): AuthUser? {
        return memberQueryPort.findAuthUserByUserid(userid)
    }

    fun findRefreshTokenByUserid(userid: String): String? {
        return tokenQueryPort.findRefreshTokenByUserid(userid)
    }
}
