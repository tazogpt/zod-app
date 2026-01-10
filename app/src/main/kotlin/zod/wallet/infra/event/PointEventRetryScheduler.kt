package zod.wallet.infra.event

import org.springframework.context.ApplicationEventPublisher
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import zod.wallet.domain.model.PointEvent

@Component
class PointEventRetryScheduler(
    private val retryCache: PointEventRetryCache,
    private val publisher: ApplicationEventPublisher,
) {

    @Scheduled(fixedDelay = 30_000)
    fun retryFailedEvents() {
        retryCache.values().forEach { event ->
            publish(event)
        }
    }

    private fun publish(event: PointEvent) {
        publisher.publishEvent(event)
    }
}
