package zod.member.infra.adapter

import org.springframework.stereotype.Repository
import zod.member.domain.port.TokenCommandRepository
import zod.member.infra.entity.TokenEntity
import zod.member.infra.jpa.TokenJpaRepository
import java.time.LocalDateTime

@Repository
class TokenCommandAdapter(
    private val tokenJpaRepository: TokenJpaRepository,
) : TokenCommandRepository {

    override fun save(userid: String, accessToken: String, refreshToken: String) {
        tokenJpaRepository.save(
            TokenEntity(
                userid = userid,
                accessToken = accessToken,
                refreshToken = refreshToken,
                updatedAt = LocalDateTime.now(),
            ),
        )
    }

}
