package zod.member.domain.port

interface TokenQueryRepository {
    fun findRefreshTokenByUserid(userid: String): String?
}