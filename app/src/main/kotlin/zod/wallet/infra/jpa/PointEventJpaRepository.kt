package zod.wallet.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import java.time.LocalDateTime
import zod.wallet.domain.enums.PointCode
import zod.wallet.infra.entity.PointEventEntity

interface PointEventJpaRepository : JpaRepository<PointEventEntity, Long> {
    fun deleteByUseridAndTypeAndRefIdAndCreatedAt(
        userid: String,
        type: PointCode,
        refId: String,
        createdAt: LocalDateTime,
    )
}
