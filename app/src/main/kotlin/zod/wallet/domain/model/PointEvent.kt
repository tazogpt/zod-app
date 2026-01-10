package zod.wallet.domain.model

import java.time.LocalDateTime
import zod.wallet.domain.enums.PointCode

data class PointEvent(
    val userid: String,
    val type: PointCode,
    val amount: Point,
    val beforeBalance: Point,
    val refId: String,
    val processedBy: String,
    val createdAt: LocalDateTime,
)
