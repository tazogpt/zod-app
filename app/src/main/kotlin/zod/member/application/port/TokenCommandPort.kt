package zod.member.application.port

interface TokenCommandPort {
    fun save(userid: String, accessToken: String, refreshToken: String)
}
