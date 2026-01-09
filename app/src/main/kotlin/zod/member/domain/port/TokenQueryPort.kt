package zod.member.domain.port

interface TokenQueryPort {
    fun findRefreshTokenByUserid(userid: String): String?
}