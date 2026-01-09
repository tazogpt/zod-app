package zod.member.application.command

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import zod.common.security.JwtTokenProvider
import zod.member.application.dto.AuthDto
import zod.member.application.port.MemberQueryPort
import zod.member.application.port.TokenCommandPort
import zod.member.application.port.TokenQueryPort

@Service
class AuthService(
    private val memberQueryPort: MemberQueryPort,
    private val tokenCommandPort: TokenCommandPort,
    private val tokenQueryPort: TokenQueryPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @Transactional
    fun login(userid: String, password: String): AuthDto.ResultTokens {
        val member = memberQueryPort.findLoginUserByUserid(userid)
            ?: throw ApiException(ErrorCode.UNAUTHORIZED)

        if (!passwordEncoder.matches(password, member.password)) {
            throw ApiException(ErrorCode.UNAUTHORIZED)
        }

        val accessToken = jwtTokenProvider.createAccessToken(
            member.userid,
            member.nickname,
            member.role.name,
            member.level,
        )
        val refreshToken = jwtTokenProvider.createRefreshToken(
            member.userid,
        )
        tokenCommandPort.save(member.userid, accessToken, refreshToken)
        return AuthDto.ResultTokens(accessToken, refreshToken)
    }

    @Transactional
    fun refresh(refreshToken: String): AuthDto.ResultTokens {
        val claims = jwtTokenProvider.parseClaims(refreshToken)
        val userid = claims.subject
        val storedRefreshToken = tokenQueryPort.findRefreshTokenByUserid(userid)
            ?: throw ApiException(ErrorCode.TOKEN_INVALID)
        if (storedRefreshToken != refreshToken) {
            throw ApiException(ErrorCode.TOKEN_INVALID)
        }
        val member = memberQueryPort.findLoginUserByUserid(userid)
            ?: throw ApiException(ErrorCode.TOKEN_INVALID)

        val accessToken = jwtTokenProvider.createAccessToken(
            member.userid,
            member.nickname,
            member.role.name,
            member.level,
        )
        val newRefreshToken = jwtTokenProvider.createRefreshToken(member.userid)
        tokenCommandPort.save(userid, accessToken, newRefreshToken)
        return AuthDto.ResultTokens(accessToken, newRefreshToken)
    }

    fun logout() {

    }
}
