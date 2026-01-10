package zod.wallet.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import zod.wallet.infra.entity.MoneyHistoryEntity

interface MoneyHistoryJpaRepository : JpaRepository<MoneyHistoryEntity, Long>
