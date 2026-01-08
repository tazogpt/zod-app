package zod.api.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zod.common.request.LoginRequest
import zod.common.request.RefreshTokenRequest
import zod.common.response.ApiResponse
import zod.common.response.TokenResponse
import zod.member.application.command.AuthService

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: LoginRequest): ResponseEntity<ApiResponse<TokenResponse>> {
        val result = authService.login(request.username, request.password)
        return ApiResponse.success(TokenResponse(result.accessToken, result.refreshToken))
            .toResponseEntity()
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<ApiResponse<Nothing>> {
        authService.logout()
        return ApiResponse.success().toResponseEntity()
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: RefreshTokenRequest): ResponseEntity<ApiResponse<TokenResponse>> {
        val result = authService.refresh(request.refreshToken)
        return ApiResponse.success(TokenResponse(result.accessToken, result.refreshToken))
            .toResponseEntity()
    }
}
