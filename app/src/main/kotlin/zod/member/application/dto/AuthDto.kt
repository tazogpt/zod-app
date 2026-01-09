package zod.member.application.dto

class AuthDto {

    data class LoginRequest(
        val userid: String,
        val password: String,
    )

    data class RefreshRequest(
        val refreshToken: String,
    )

    data class TokenResponse(
        val accessToken: String,
        val refreshToken: String,
    )

    data class ResultTokens(
        val accessToken: String,
        val refreshToken: String,
    )
}