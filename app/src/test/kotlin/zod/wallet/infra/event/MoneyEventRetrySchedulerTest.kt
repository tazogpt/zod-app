package zod.wallet.infra.event

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.time.LocalDateTime
import org.springframework.context.ApplicationEventPublisher
import zod.wallet.domain.enums.MoneyCode
import zod.wallet.domain.model.Money
import zod.wallet.domain.model.MoneyEvent

class MoneyEventRetrySchedulerTest {

    @Test
    fun `캐시에 있는 이벤트를 다시 발행한다`() {
        val cache = MoneyEventRetryCache()
        val publisher = mock(ApplicationEventPublisher::class.java)
        val scheduler = MoneyEventRetryScheduler(cache, publisher)
        val event = MoneyEvent(
            userid = "user-1",
            type = MoneyCode.DEPOSIT,
            amount = Money(1000),
            beforeBalance = Money(5000),
            refId = "banking-1",
            processedBy = "admin",
            createdAt = LocalDateTime.now(),
        )

        cache.put(event)

        scheduler.retryFailedEvents()

        verify(publisher).publishEvent(event)
    }
}
