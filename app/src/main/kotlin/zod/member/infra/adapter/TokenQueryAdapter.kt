package zod.member.infra.adapter

import org.springframework.stereotype.Repository
import zod.member.domain.port.TokenQueryPort
import zod.member.infra.jpa.TokenJpaRepository

@Repository
class TokenQueryAdapter(
    private val tokenJpaRepository: TokenJpaRepository,
) : TokenQueryPort {

    override fun findRefreshTokenByUserid(userid: String): String? {
        return tokenJpaRepository.findByUserid(userid)?.refreshToken
    }
}
