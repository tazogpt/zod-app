package zod.api.auth

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zod.common.response.ApiResponse
import zod.member.application.command.SignupCommandService
import zod.member.application.dto.AuthDto

@RestController
@RequestMapping("/api/auth")
class SignupController(
    private val signupCommandService: SignupCommandService,
) {

    @PostMapping("/signup")
    fun signup(@RequestBody request: AuthDto.SignupRequest): ResponseEntity<ApiResponse<Nothing>> {
        signupCommandService.signup(request.userid, request.nickname, request.password)
        return ApiResponse<Nothing>(message = "가입 신청이 완료되었습니다.").toResponseEntity()
    }
}
