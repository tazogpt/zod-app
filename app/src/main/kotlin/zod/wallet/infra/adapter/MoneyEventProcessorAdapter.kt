package zod.wallet.infra.adapter

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import zod.wallet.application.port.MoneyEventProcessor
import zod.wallet.domain.model.MoneyEvent
import zod.wallet.infra.entity.MoneyHistoryEntity
import zod.wallet.infra.jpa.MoneyEventJpaRepository
import zod.wallet.infra.jpa.MoneyHistoryJpaRepository
import zod.wallet.infra.jpa.WalletJpaRepository

@Repository
class MoneyEventProcessorAdapter(
    private val walletJpaRepository: WalletJpaRepository,
    private val moneyEventJpaRepository: MoneyEventJpaRepository,
    private val moneyHistoryJpaRepository: MoneyHistoryJpaRepository,
) : MoneyEventProcessor {

    @Transactional
    override fun process(event: MoneyEvent) {
        val wallet = walletJpaRepository.findById(event.userid)
            .orElseThrow { IllegalStateException("지갑이 존재하지 않습니다. userid=${event.userid}") }

        val afterBalance = wallet.money + event.amount.value
        wallet.money = afterBalance

        moneyHistoryJpaRepository.save(
            MoneyHistoryEntity(
                userid = event.userid,
                type = event.type,
                amount = event.amount.value,
                beforeBalance = event.beforeBalance.value,
                afterBalance = afterBalance,
                refId = event.refId,
                processedBy = event.processedBy,
                createdAt = event.createdAt,
            ),
        )

        moneyEventJpaRepository.deleteByUseridAndTypeAndRefIdAndCreatedAt(
            userid = event.userid,
            type = event.type,
            refId = event.refId,
            createdAt = event.createdAt,
        )
    }
}
