package zod.member.infra.adapter

import org.springframework.stereotype.Repository
import zod.member.application.port.TokenQueryPort
import zod.member.infra.cache.TokenCache
import zod.member.infra.jpa.TokenJpaRepository

@Repository
class TokenQueryAdapter(
    private val tokenJpaRepository: TokenJpaRepository,
    private val tokenCache: TokenCache,
) : TokenQueryPort {

    override fun findRefreshTokenByUserid(userid: String): String? {
        tokenCache.get(userid)?.let { return it }

        val refreshToken = tokenJpaRepository.findByUserid(userid)?.refreshToken
        if (refreshToken != null) {
            tokenCache.put(userid, refreshToken)
        }
        return refreshToken
    }
}
