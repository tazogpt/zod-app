package zod.member.infra.adapter

import org.springframework.stereotype.Repository
import org.springframework.transaction.support.TransactionSynchronization
import org.springframework.transaction.support.TransactionSynchronizationManager
import zod.member.application.port.TokenCommandPort
import zod.member.infra.cache.TokenCache
import zod.member.infra.entity.TokenEntity
import zod.member.infra.jpa.TokenJpaRepository
import java.time.LocalDateTime

@Repository
class TokenCommandAdapter(
    private val tokenJpaRepository: TokenJpaRepository,
    private val tokenCache: TokenCache,
) : TokenCommandPort {

    override fun save(userid: String, accessToken: String, refreshToken: String) {
        tokenJpaRepository.save(
            TokenEntity(
                userid = userid,
                accessToken = accessToken,
                refreshToken = refreshToken,
                updatedAt = LocalDateTime.now(),
            ),
        )
        executeAfterCommit { tokenCache.put(userid, refreshToken) }
    }

    override fun deleteByUserid(userid: String) {
        tokenJpaRepository.deleteById(userid)
        executeAfterCommit { tokenCache.evict(userid) }
    }

    private fun executeAfterCommit(action: () -> Unit) {
        if (TransactionSynchronizationManager.isSynchronizationActive()) {
            TransactionSynchronizationManager.registerSynchronization(object : TransactionSynchronization {
                override fun afterCommit() {
                    action()
                }
            })
        } else {
            action()
        }
    }
}
