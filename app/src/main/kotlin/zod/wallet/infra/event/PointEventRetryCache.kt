package zod.wallet.infra.event

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Component
import zod.wallet.domain.model.PointEvent

@Component
class PointEventRetryCache {
    private val cache: Cache<String, PointEvent> = Caffeine.newBuilder()
        .build()

    fun put(event: PointEvent) {
        cache.put(keyOf(event), event)
    }

    fun remove(event: PointEvent) {
        cache.invalidate(keyOf(event))
    }

    fun values(): List<PointEvent> = cache.asMap().values.toList()

    fun size(): Long = cache.estimatedSize()

    private fun keyOf(event: PointEvent): String =
        "${event.refId}:${event.type}:${event.createdAt}"
}
