package zod.wallet.infra.event

import org.springframework.scheduling.annotation.Async
import org.springframework.stereotype.Component
import org.springframework.transaction.event.TransactionPhase
import org.springframework.transaction.event.TransactionalEventListener
import zod.wallet.application.port.MoneyEventProcessor
import zod.wallet.domain.model.MoneyEvent

@Component
class MoneyEventListener(
    private val processor: MoneyEventProcessor,
    private val retryCache: MoneyEventRetryCache,
) {

    @Async("paymentExecutor")
    @TransactionalEventListener(phase = TransactionPhase.AFTER_COMMIT, fallbackExecution = true)
    fun handle(event: MoneyEvent) {
        try {
            processor.process(event)
            retryCache.remove(event)
        } catch (ex: Exception) {
            retryCache.put(event)
            throw ex
        }
    }
}
