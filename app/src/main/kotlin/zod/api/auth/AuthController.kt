package zod.api.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zod.common.response.ApiResponse
import zod.member.application.command.AuthService
import zod.member.application.dto.AuthDto

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authService: AuthService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: AuthDto.LoginRequest): ResponseEntity<ApiResponse<AuthDto.TokenResponse>> {
        val result = authService.login(request.userid, request.password)
        return ApiResponse.success(AuthDto.TokenResponse(result.accessToken, result.refreshToken))
            .toResponseEntity()
    }

    @PostMapping("/logout")
    fun logout(): ResponseEntity<ApiResponse<Nothing>> {
        authService.logout()
        return ApiResponse.success().toResponseEntity()
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: AuthDto.RefreshRequest): ResponseEntity<ApiResponse<AuthDto.TokenResponse>> {
        val result = authService.refresh(request.refreshToken)
        return ApiResponse.success(AuthDto.TokenResponse(result.accessToken, result.refreshToken))
            .toResponseEntity()
    }
}
