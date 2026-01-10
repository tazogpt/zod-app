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

        validateMemberActive(member, ErrorCode.UNAUTHORIZED)
        validatePassword(password, member.password)

        return issueAndSaveTokens(member)
    }

    @Transactional
    fun logout(refreshToken: String) {
        val userid = extractUseridFromTokenAllowExpired(refreshToken)
        val storedRefreshToken = authQueryService.findRefreshTokenByUserid(userid)

        if (storedRefreshToken != null && storedRefreshToken == refreshToken) {
            tokenCommandPort.deleteByUserid(userid)
        }
    }

    @Transactional
    fun refresh(refreshToken: String): AuthDto.ResultTokens {
        val userid = extractUseridFromToken(refreshToken)
        val storedRefreshToken = getStoredRefreshToken(userid)

        validateRefreshTokenMatch(storedRefreshToken, refreshToken)

        val member = authQueryService.findLoginUserByUserid(userid)
            ?: throw ApiException(ErrorCode.TOKEN_INVALID)

        validateMemberActive(member, ErrorCode.TOKEN_INVALID)

        return issueAndSaveTokens(member)
    }

    private fun validateMemberActive(member: AuthUser, errorCode: ErrorCode) {
        if (member.status != MemberStatus.ACTIVE) {
            throw ApiException(errorCode)
        }
    }

    private fun validatePassword(rawPassword: String, encodedPassword: String) {
        if (!passwordEncoder.matches(rawPassword, encodedPassword)) {
            throw ApiException(ErrorCode.UNAUTHORIZED)
        }
    }

    private fun extractUseridFromToken(refreshToken: String): String {
        val claims = jwtTokenProvider.parseClaims(refreshToken)
        return claims.subject
    }

    private fun extractUseridFromTokenAllowExpired(refreshToken: String): String {
        val claims = jwtTokenProvider.parseClaimsAllowExpired(refreshToken)
        return claims.subject
    }

    private fun getStoredRefreshToken(userid: String): String {
        return authQueryService.findRefreshTokenByUserid(userid)
            ?: throw ApiException(ErrorCode.TOKEN_INVALID)
    }

    private fun validateRefreshTokenMatch(stored: String, requested: String) {
        if (stored != requested) {
            throw ApiException(ErrorCode.TOKEN_INVALID)
        }
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
