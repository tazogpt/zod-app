package zod.member.infra.adapter

import org.springframework.stereotype.Repository
import zod.member.domain.port.TokenQueryRepository
import zod.member.infra.jpa.TokenJpaRepository

@Repository
class TokenQueryAdapter(
    private val tokenJpaRepository: TokenJpaRepository,
) : TokenQueryRepository {

    override fun findRefreshTokenByUserid(userid: String): String? {
        return tokenJpaRepository.findByUserid(userid)?.refreshToken
    }
}
