package zod.member.application.command

data class AuthTokenResult(
    val accessToken: String,
    val refreshToken: String,
)
