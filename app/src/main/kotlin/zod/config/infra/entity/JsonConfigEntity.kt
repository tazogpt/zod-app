package zod.config.infra.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.Id
import jakarta.persistence.Lob
import jakarta.persistence.Table

@Entity
@Table(name = "JSON_CONFIG")
class JsonConfigEntity(

    @Id
    @Column(length = 31, nullable = false)
    val code: String = "",

    @Lob
    @Column(nullable = false)
    val json: String = "",
)
