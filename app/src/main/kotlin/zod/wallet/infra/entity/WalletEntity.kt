package zod.wallet.infra.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table

@Entity
@Table(name = "wallet")
class WalletEntity(
    @Id
    @Column(length = 63, nullable = false)
    val userid: String,

    @Column(nullable = false)
    var money: Long,

    @Column(nullable = false)
    var point: Long,
)
