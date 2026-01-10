package zod.wallet.infra.event

import org.junit.jupiter.api.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import java.time.LocalDateTime
import org.springframework.context.ApplicationEventPublisher
import zod.wallet.domain.enums.PointCode
import zod.wallet.domain.model.Point
import zod.wallet.domain.model.PointEvent

class PointEventRetrySchedulerTest {

    @Test
    fun `캐시에 있는 이벤트를 다시 발행한다`() {
        val cache = PointEventRetryCache()
        val publisher = mock(ApplicationEventPublisher::class.java)
        val scheduler = PointEventRetryScheduler(cache, publisher)
        val event = PointEvent(
            userid = "user-1",
            type = PointCode.DEPOSIT,
            amount = Point(100),
            beforeBalance = Point(5000),
            refId = "banking-1",
            processedBy = "admin",
            createdAt = LocalDateTime.now(),
        )

        cache.put(event)

        scheduler.retryFailedEvents()

        verify(publisher).publishEvent(event)
    }
}
