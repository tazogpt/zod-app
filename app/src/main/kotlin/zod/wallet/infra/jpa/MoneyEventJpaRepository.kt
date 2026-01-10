package zod.wallet.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import zod.wallet.domain.enums.MoneyCode
import zod.wallet.infra.entity.MoneyEventEntity

interface MoneyEventJpaRepository : JpaRepository<MoneyEventEntity, Long> {
    fun deleteByUseridAndTypeAndRefIdAndCreatedAt(
        userid: String,
        type: MoneyCode,
        refId: String,
        createdAt: LocalDateTime,
    )
}
