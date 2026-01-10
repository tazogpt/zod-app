package zod.wallet.infra.event

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Component
import zod.wallet.domain.model.MoneyEvent

@Component
class MoneyEventRetryCache {
    private val cache: Cache<String, MoneyEvent> = Caffeine.newBuilder()
        .build()

    fun put(event: MoneyEvent) {
        cache.put(keyOf(event), event)
    }

    fun remove(event: MoneyEvent) {
        cache.invalidate(keyOf(event))
    }

    fun values(): List<MoneyEvent> = cache.asMap().values.toList()

    fun size(): Long = cache.estimatedSize()

    private fun keyOf(event: MoneyEvent): String =
        "${event.refId}:${event.type}:${event.createdAt}"
}
