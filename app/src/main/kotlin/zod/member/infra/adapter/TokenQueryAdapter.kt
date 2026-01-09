package zod.member.infra.adapter

import org.springframework.stereotype.Repository
import zod.member.application.port.TokenQueryPort
import zod.member.infra.jpa.TokenJpaRepository

@Repository
class TokenQueryAdapter(
    private val tokenJpaRepository: TokenJpaRepository,
) : TokenQueryPort {

    override fun findRefreshTokenByUserid(userid: String): String? {
        return tokenJpaRepository.findByUserid(userid)?.refreshToken
    }
}
