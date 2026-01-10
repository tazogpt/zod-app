package zod.wallet.infra.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime
import zod.wallet.domain.enums.MoneyCode

@Entity
@Table(name = "money_history")
class MoneyHistoryEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 63, nullable = false)
    val userid: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 63, nullable = false)
    val type: MoneyCode,

    @Column(nullable = false)
    val amount: Long,

    @Column(nullable = false)
    val beforeBalance: Long,

    @Column(nullable = false)
    val afterBalance: Long,

    @Column(length = 63, nullable = false)
    val refId: String,

    @Column(length = 63, nullable = false)
    val processedBy: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime,
)
