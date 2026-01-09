package zod.member.domain.port

interface TokenCommandPort {
    fun save(userid: String, accessToken: String, refreshToken: String)
}