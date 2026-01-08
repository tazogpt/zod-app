package zod.member.application.command

import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zod.common.error.ErrorCode
import zod.common.error.exception.ApiException
import zod.common.security.JwtTokenProvider
import zod.member.domain.port.MemberQueryRepository
import zod.member.domain.port.TokenCommandRepository
import zod.member.domain.port.TokenQueryRepository

@Service
class AuthService(
    private val memberQueryRepository: MemberQueryRepository,
    private val tokenCommandRepository: TokenCommandRepository,
    private val tokenQueryRepository: TokenQueryRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtTokenProvider: JwtTokenProvider,
) {
    @Transactional
    fun login(username: String, password: String): AuthTokenResult {
        val member = memberQueryRepository.findByUserid(username)
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
        tokenCommandRepository.save(member.userid, accessToken, refreshToken)
        return AuthTokenResult(accessToken, refreshToken)
    }

    @Transactional
    fun refresh(refreshToken: String): AuthTokenResult {
        val claims = jwtTokenProvider.parseClaims(refreshToken)
        val userid = claims.subject
        val storedRefreshToken = tokenQueryRepository.findRefreshTokenByUserid(userid)
            ?: throw ApiException(ErrorCode.TOKEN_INVALID)
        if (storedRefreshToken != refreshToken) {
            throw ApiException(ErrorCode.TOKEN_INVALID)
        }
        val member = memberQueryRepository.findByUserid(userid)
            ?: throw ApiException(ErrorCode.TOKEN_INVALID)

        val accessToken = jwtTokenProvider.createAccessToken(
            member.userid,
            member.nickname,
            member.role.name,
            member.level,
        )
        val newRefreshToken = jwtTokenProvider.createRefreshToken(member.userid)
        tokenCommandRepository.save(userid, accessToken, newRefreshToken)
        return AuthTokenResult(accessToken, newRefreshToken)
    }

    fun logout() {
    }
}
