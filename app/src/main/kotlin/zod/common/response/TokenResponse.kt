package zod.common.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)