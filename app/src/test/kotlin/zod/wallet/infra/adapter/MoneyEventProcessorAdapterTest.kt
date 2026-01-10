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
import zod.wallet.domain.enums.MoneyCode
import zod.wallet.domain.model.Money
import zod.wallet.domain.model.MoneyEvent
import zod.wallet.infra.entity.MoneyHistoryEntity
import zod.wallet.infra.entity.WalletEntity
import zod.wallet.infra.jpa.MoneyEventJpaRepository
import zod.wallet.infra.jpa.MoneyHistoryJpaRepository
import zod.wallet.infra.jpa.WalletJpaRepository

class MoneyEventProcessorAdapterTest {

    @Test
    fun `머니 이벤트 처리 시 지갑 업데이트와 히스토리 기록 후 이벤트를 삭제한다`() {
        val walletRepository = mock(WalletJpaRepository::class.java)
        val eventRepository = mock(MoneyEventJpaRepository::class.java)
        val historyRepository = mock(MoneyHistoryJpaRepository::class.java)
        val processor = MoneyEventProcessorAdapter(walletRepository, eventRepository, historyRepository)
        val wallet = WalletEntity(userid = "user-1", money = 1000, point = 0)
        val event = MoneyEvent(
            userid = "user-1",
            type = MoneyCode.DEPOSIT,
            amount = Money(500),
            beforeBalance = Money(1000),
            refId = "banking-1",
            processedBy = "admin",
            createdAt = LocalDateTime.now(),
        )

        `when`(walletRepository.findById("user-1")).thenReturn(Optional.of(wallet))

        processor.process(event)

        assertEquals(1500, wallet.money)

        val historyCaptor = ArgumentCaptor.forClass(MoneyHistoryEntity::class.java)
        verify(historyRepository).save(historyCaptor.capture())
        assertEquals(1500, historyCaptor.value.afterBalance)
        assertEquals("admin", historyCaptor.value.processedBy)

        verify(eventRepository).deleteByUseridAndTypeAndRefIdAndCreatedAt(
            userid = "user-1",
            type = MoneyCode.DEPOSIT,
            refId = "banking-1",
            createdAt = event.createdAt,
        )
    }

    @Test
    fun `지갑이 없으면 처리에 실패하고 이벤트를 삭제하지 않는다`() {
        val walletRepository = mock(WalletJpaRepository::class.java)
        val eventRepository = mock(MoneyEventJpaRepository::class.java)
        val historyRepository = mock(MoneyHistoryJpaRepository::class.java)
        val processor = MoneyEventProcessorAdapter(walletRepository, eventRepository, historyRepository)
        val event = MoneyEvent(
            userid = "user-1",
            type = MoneyCode.DEPOSIT,
            amount = Money(500),
            beforeBalance = Money(1000),
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
            type = MoneyCode.DEPOSIT,
            refId = "banking-1",
            createdAt = event.createdAt,
        )
    }
}
