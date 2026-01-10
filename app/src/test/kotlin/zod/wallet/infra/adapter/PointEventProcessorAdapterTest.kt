package zod.wallet.infra.adapter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.Test
import org.mockito.ArgumentCaptor
import org.mockito.Mockito.mock
import org.mockito.Mockito.never
import org.mockito.Mockito.verify
import org.mockito.Mockito.`when`
import java.time.LocalDateTime
import java.util.Optional
import zod.wallet.domain.enums.PointCode
import zod.wallet.domain.model.Point
import zod.wallet.domain.model.PointEvent
import zod.wallet.infra.entity.PointHistoryEntity
import zod.wallet.infra.entity.WalletEntity
import zod.wallet.infra.jpa.PointEventJpaRepository
import zod.wallet.infra.jpa.PointHistoryJpaRepository
import zod.wallet.infra.jpa.WalletJpaRepository

class PointEventProcessorAdapterTest {

    @Test
    fun `포인트 이벤트 처리 시 지갑 업데이트와 히스토리 기록 후 이벤트를 삭제한다`() {
        val walletRepository = mock(WalletJpaRepository::class.java)
        val eventRepository = mock(PointEventJpaRepository::class.java)
        val historyRepository = mock(PointHistoryJpaRepository::class.java)
        val processor = PointEventProcessorAdapter(walletRepository, eventRepository, historyRepository)
        val wallet = WalletEntity(userid = "user-1", money = 0, point = 200)
        val event = PointEvent(
            userid = "user-1",
            type = PointCode.DEPOSIT,
            amount = Point(50),
            beforeBalance = Point(200),
            refId = "banking-1",
            processedBy = "admin",
            createdAt = LocalDateTime.now(),
        )

        `when`(walletRepository.findById("user-1")).thenReturn(Optional.of(wallet))

        processor.process(event)

        assertEquals(250, wallet.point)

        val historyCaptor = ArgumentCaptor.forClass(PointHistoryEntity::class.java)
        verify(historyRepository).save(historyCaptor.capture())
        assertEquals(250, historyCaptor.value.afterBalance)
        assertEquals("admin", historyCaptor.value.processedBy)

        verify(eventRepository).deleteByUseridAndTypeAndRefIdAndCreatedAt(
            userid = "user-1",
            type = PointCode.DEPOSIT,
            refId = "banking-1",
            createdAt = event.createdAt,
        )
    }

    @Test
    fun `지갑이 없으면 처리에 실패하고 이벤트를 삭제하지 않는다`() {
        val walletRepository = mock(WalletJpaRepository::class.java)
        val eventRepository = mock(PointEventJpaRepository::class.java)
        val historyRepository = mock(PointHistoryJpaRepository::class.java)
        val processor = PointEventProcessorAdapter(walletRepository, eventRepository, historyRepository)
        val event = PointEvent(
            userid = "user-1",
            type = PointCode.DEPOSIT,
            amount = Point(50),
            beforeBalance = Point(200),
            refId = "banking-1",
            processedBy = "admin",
            createdAt = LocalDateTime.now(),
        )

        `when`(walletRepository.findById("user-1")).thenReturn(Optional.empty())

        assertThrows(IllegalStateException::class.java) {
            processor.process(event)
        }

        verify(eventRepository, never()).deleteByUseridAndTypeAndRefIdAndCreatedAt(
            userid = "user-1",
            type = PointCode.DEPOSIT,
            refId = "banking-1",
            createdAt = event.createdAt,
        )
    }
}
