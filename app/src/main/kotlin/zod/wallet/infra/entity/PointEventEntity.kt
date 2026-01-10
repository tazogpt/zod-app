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
import zod.wallet.domain.enums.PointCode

@Entity
@Table(name = "point_event")
class PointEventEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(length = 63, nullable = false)
    val userid: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 63, nullable = false)
    val type: PointCode,

    @Column(nullable = false)
    val amount: Long,

    @Column(nullable = false)
    val beforeBalance: Long,

    @Column(length = 63, nullable = false)
    val refId: String,

    @Column(length = 63, nullable = false)
    val processedBy: String,

    @Column(nullable = false)
    val createdAt: LocalDateTime,
)
