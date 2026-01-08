package zod.member.infra.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Table
import java.time.LocalDateTime

@Entity
@Table(name = "token")
data class TokenEntity(
    @Id
    @Column(length = 63, nullable = false)
    val userid: String,

    @Column(length = 511, nullable = false)
    val accessToken: String,

    @Column(length = 511, nullable = false)
    val refreshToken: String,

    @Column(nullable = false)
    val updatedAt: LocalDateTime,
)