package zod.wallet.infra.adapter

import org.springframework.stereotype.Repository
import org.springframework.transaction.annotation.Transactional
import zod.wallet.application.port.PointEventProcessor
import zod.wallet.domain.model.PointEvent
import zod.wallet.infra.entity.PointHistoryEntity
import zod.wallet.infra.jpa.PointEventJpaRepository
import zod.wallet.infra.jpa.PointHistoryJpaRepository
import zod.wallet.infra.jpa.WalletJpaRepository

@Repository
class PointEventProcessorAdapter(
    private val walletJpaRepository: WalletJpaRepository,
    private val pointEventJpaRepository: PointEventJpaRepository,
    private val pointHistoryJpaRepository: PointHistoryJpaRepository,
) : PointEventProcessor {

    @Transactional
    override fun process(event: PointEvent) {
        val wallet = walletJpaRepository.findById(event.userid)
            .orElseThrow { IllegalStateException("지갑이 존재하지 않습니다. userid=${event.userid}") }

        val afterBalance = wallet.point + event.amount.value
        wallet.point = afterBalance

        pointHistoryJpaRepository.save(
            PointHistoryEntity(
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

        pointEventJpaRepository.deleteByUseridAndTypeAndRefIdAndCreatedAt(
            userid = event.userid,
            type = event.type,
            refId = event.refId,
            createdAt = event.createdAt,
        )
    }
}
