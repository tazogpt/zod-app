package zod.wallet.domain.model

import java.time.LocalDateTime
import zod.wallet.domain.enums.MoneyCode

data class MoneyEvent(
    val userid: String,
    val type: MoneyCode,
    val amount: Money,
    val beforeBalance: Money,
    val refId: String,
    val processedBy: String,
    val createdAt: LocalDateTime,
)
