package zod.member.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import zod.member.infra.entity.TokenEntity
import java.time.LocalDateTime

interface TokenJpaRepository : JpaRepository<TokenEntity, String> {
    fun findByUserid(userid: String): TokenEntity?
    fun findByUpdatedAtAfter(updatedAt: LocalDateTime): List<TokenEntity>
}
