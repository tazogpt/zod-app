package zod.member.application.command

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import zod.common.security.JwtTokenProvider
import zod.member.application.dto.AuthDto
import zod.member.application.port.TokenCommandPort
import zod.member.application.query.AuthQueryService
import zod.member.application.query.model.AuthUser
import zod.member.domain.enums.MemberStatus
import java.util.Date

@Service
class AuthCommandService(
    private val authQueryService: AuthQueryService,
    private val tokenCommandPort: TokenCommandPort,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {

    @Transactional
    fun login(userid: String, password: String): AuthDto.ResultTokens {
        val member = authQueryService.findLoginUserByUserid(userid)
            ?: throw ApiException(ErrorCode.UNAUTHORIZED)

        if (member.status != MemberStatus.ACTIVE) {
            throw ApiException(ErrorCode.UNAUTHORIZED)
        }

        if (!passwordEncoder.matches(password, member.password)) {
            throw ApiException(ErrorCode.UNAUTHORIZED)
        }

        return issueAndSaveTokens(member)
    }

    @Transactional
    fun logout(userid: String, refreshToken: String) {
        val storedRefreshToken = authQueryService.findRefreshTokenByUserid(userid)

        if (storedRefreshToken != null && storedRefreshToken == refreshToken) {
            tokenCommandPort.deleteByUserid(userid)
        }
    }

    @Transactional
    fun refresh(refreshToken: String): AuthDto.ResultTokens {
        val claims = jwtTokenProvider.parseRefreshClaimsAllowExpired(refreshToken)
        if (claims.expiration.before(Date())) {
            throw ApiException(ErrorCode.TOKEN_EXPIRED)
        }
        val userid = claims.subject

        val storedRefreshToken = authQueryService.findRefreshTokenByUserid(userid)
            ?: throw ApiException(ErrorCode.TOKEN_INVALID)

        if (storedRefreshToken != refreshToken) {
            throw ApiException(ErrorCode.TOKEN_INVALID)
        }

        val member = authQueryService.findLoginUserByUserid(userid)
            ?: throw ApiException(ErrorCode.TOKEN_INVALID)

        if (member.status != MemberStatus.ACTIVE) {
            throw ApiException(ErrorCode.TOKEN_INVALID)
        }

        return issueAndSaveTokens(member)
    }

    private fun issueAndSaveTokens(member: AuthUser): AuthDto.ResultTokens {
        val accessToken = jwtTokenProvider.createAccessToken(
            member.userid,
            member.nickname,
            member.role.name,
            member.level,
        )
        val refreshToken = jwtTokenProvider.createRefreshToken(member.userid)
        tokenCommandPort.save(member.userid, accessToken, refreshToken)

        return AuthDto.ResultTokens(accessToken, refreshToken)
    }
}
