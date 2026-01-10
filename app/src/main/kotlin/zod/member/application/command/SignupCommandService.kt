package zod.member.application.command

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import zod.member.application.port.MemberQueryPort
import zod.member.application.port.SignupCommandPort
import zod.member.domain.model.SignupRequest
import java.time.LocalDateTime

@Service
class SignupCommandService(
    private val memberQueryPort: MemberQueryPort,
    private val signupCommandPort: SignupCommandPort,
    private val passwordEncoder: PasswordEncoder,
) {

    @Transactional
    fun signup(userid: String, nickname: String, password: String) {
        if (password.length < 4) {
            throw ApiException(ErrorCode.INVALID_PASSWORD)
        }

        if (memberQueryPort.existsByUserid(userid)) {
            throw ApiException(ErrorCode.DUPLICATE_USERID)
        }

        if (memberQueryPort.existsByNickname(nickname)) {
            throw ApiException(ErrorCode.DUPLICATE_NICKNAME)
        }

        val encodedPassword = requireNotNull(passwordEncoder.encode(password))
        signupCommandPort.save(
            SignupRequest(
                userid = userid,
                nickname = nickname,
                password = encodedPassword,
                requestedAt = LocalDateTime.now(),
            ),
        )
    }
}
