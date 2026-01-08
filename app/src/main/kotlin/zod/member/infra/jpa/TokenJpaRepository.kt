package zod.member.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import zod.member.infra.entity.TokenEntity

interface TokenJpaRepository : JpaRepository<TokenEntity, String> {
    fun findByUserid(userid: String): TokenEntity?
}
