package zod.wallet.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import zod.wallet.infra.entity.WalletEntity

interface WalletJpaRepository : JpaRepository<WalletEntity, String>
