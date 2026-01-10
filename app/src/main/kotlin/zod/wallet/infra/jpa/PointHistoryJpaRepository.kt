package zod.wallet.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import zod.wallet.infra.entity.PointHistoryEntity

interface PointHistoryJpaRepository : JpaRepository<PointHistoryEntity, Long>
