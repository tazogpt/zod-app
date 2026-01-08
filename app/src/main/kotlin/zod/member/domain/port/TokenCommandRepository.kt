package zod.member.domain.port

interface TokenCommandRepository {
    fun save(userid: String, accessToken: String, refreshToken: String)
}