package zod.member.infra.cache

import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.beans.factory.annotation.Value
import zod.member.infra.jpa.TokenJpaRepository
import java.time.LocalDateTime

@Component
class TokenCacheInitializer(
    @Value("\${security.jwt.refresh-mins}") private val refreshMinutes: Long,
    private val tokenJpaRepository: TokenJpaRepository,
    private val tokenCache: TokenCache,
) {

    @Async
    @EventListener(ApplicationReadyEvent::class)
    fun warmup() {
        val cutoff = LocalDateTime.now().minusMinutes(refreshMinutes)
        tokenJpaRepository.findByUpdatedAtAfter(cutoff).forEach { token ->
            tokenCache.put(token.userid, token.refreshToken)
        }
    }
}
