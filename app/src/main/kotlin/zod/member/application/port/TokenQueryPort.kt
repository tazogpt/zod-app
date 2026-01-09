package zod.member.application.port

interface TokenQueryPort {
    fun findRefreshTokenByUserid(userid: String): String?
}
