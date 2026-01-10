package zod.wallet.infra.event

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import zod.wallet.application.port.PointEventProcessor
import zod.wallet.domain.model.PointEvent

@Component
class PointEventListener(
    private val processor: PointEventProcessor,
    private val retryCache: PointEventRetryCache,
) {

    @Async("paymentExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    fun handle(event: PointEvent) {
        try {
            processor.process(event)
            retryCache.remove(event)
        } catch (ex: Exception) {
            retryCache.put(event)
            throw ex
        }
    }
}
