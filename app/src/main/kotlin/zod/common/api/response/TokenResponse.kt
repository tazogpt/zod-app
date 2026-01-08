package zod.common.api.response

data class TokenResponse(
    val accessToken: String,
    val refreshToken: String,
)