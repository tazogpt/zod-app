package zod.wallet.infra.event

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.Mockito.doThrow
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify
import org.mockito.Mockito.verifyNoMoreInteractions
import java.time.LocalDateTime
import zod.wallet.application.port.PointEventProcessor
import zod.wallet.domain.enums.PointCode
import zod.wallet.domain.model.Point
import zod.wallet.domain.model.PointEvent

class PointEventListenerTest {

    @Test
    fun `처리 실패 시 재시도 캐시에 저장한다`() {
        val processor = mock(PointEventProcessor::class.java)
        val cache = PointEventRetryCache()
        val listener = PointEventListener(processor, cache)
        val event = PointEvent(
            userid = "user-1",
            type = PointCode.DEPOSIT,
            amount = Point(100),
            beforeBalance = Point(5000),
            refId = "banking-1",
            processedBy = "admin",
            createdAt = LocalDateTime.now(),
        )

        doThrow(IllegalStateException("fail")).`when`(processor).process(event)

        assertThrows(IllegalStateException::class.java) {
            listener.handle(event)
        }

        assertEquals(1, cache.size())
    }

    @Test
    fun `처리 성공 시 캐시에서 제거한다`() {
        val processor = mock(PointEventProcessor::class.java)
        val cache = PointEventRetryCache()
        val listener = PointEventListener(processor, cache)
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

        listener.handle(event)

        assertEquals(0, cache.size())
        verify(processor).process(event)
        verifyNoMoreInteractions(processor)
    }
}
