package zod.api.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import zod.common.response.ApiResponse
import zod.member.application.command.AuthCommandService
import zod.member.application.dto.AuthDto
import zod.member.domain.enums.MemberRole

@RestController
@RequestMapping("/api/auth")
class AuthController(
    private val authCommandService: AuthCommandService,
) {
    @PostMapping("/login")
    fun login(@RequestBody request: AuthDto.LoginRequest): ResponseEntity<ApiResponse<AuthDto.TokenResponse>> {
        val group = when (request.channel.trim().uppercase()) {
            "" -> MemberRole.Group.USER
            "ADMIN" -> MemberRole.Group.ADMIN
            "PARTNER" -> MemberRole.Group.PARTNER
            else -> throw ApiException(ErrorCode.UNAUTHORIZED)
        }
        val result = authCommandService.login(request.userid, request.password, group)
        return ApiResponse.success(AuthDto.TokenResponse(result.accessToken, result.refreshToken))
            .toResponseEntity()
    }

    @PostMapping("/logout")
    fun logout(@RequestBody request: AuthDto.LogoutRequest): ResponseEntity<ApiResponse<Nothing>> {
        authCommandService.logout(request.userid, request.refreshToken)
        return ApiResponse.success().toResponseEntity()
    }

    @PostMapping("/refresh")
    fun refresh(@RequestBody request: AuthDto.RefreshRequest): ResponseEntity<ApiResponse<AuthDto.TokenResponse>> {
        val result = authCommandService.refresh(request.refreshToken)
        return ApiResponse.success(AuthDto.TokenResponse(result.accessToken, result.refreshToken))
            .toResponseEntity()
    }
}
