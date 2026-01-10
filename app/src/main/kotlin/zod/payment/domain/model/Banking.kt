package zod.payment.domain.model

import java.time.LocalDateTime
import zod.payment.domain.enums.BankingStatus
import zod.payment.domain.enums.BankingType
import zod.wallet.domain.model.Money
import zod.wallet.domain.model.Point

data class Banking(
    val userid: String,
    val type: BankingType,
    val amount: Money,
    val bonusPoint: Point,
    var status: BankingStatus,
    val requestedAt: LocalDateTime,
    var processedAt: LocalDateTime? = null,
    var processedBy: String? = null,
) {
    fun approve(processedBy: String, processedAt: LocalDateTime) {
        requireStatus(BankingStatus.REQUEST)
        status = BankingStatus.APPROVE
        this.processedBy = processedBy
        this.processedAt = processedAt
    }

    fun cancel(processedBy: String, processedAt: LocalDateTime) {
        requireStatus(BankingStatus.REQUEST)
        status = BankingStatus.CANCEL
        this.processedBy = processedBy
        this.processedAt = processedAt
    }

    fun rollback(processedBy: String, processedAt: LocalDateTime) {
        requireStatus(BankingStatus.APPROVE)
        status = BankingStatus.ROLLBACK
        this.processedBy = processedBy
        this.processedAt = processedAt
    }

    private fun requireStatus(expected: BankingStatus) {
        if (status != expected) {
            throw IllegalStateException("허용되지 않는 상태 전이입니다. current=$status, expected=$expected")
        }
    }
}
