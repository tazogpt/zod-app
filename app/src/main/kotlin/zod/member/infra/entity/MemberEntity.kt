package zod.member.infra.entity

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.EnumType
import jakarta.persistence.Enumerated
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.Table
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus
import zod.member.domain.model.Member

@Entity
@Table(name = "member")
data class MemberEntity(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    @Column(length = 63, unique = true, nullable = false)
    val userid: String,

    @Column(length = 63, unique = true, nullable = false)
    val nickname: String,

    @Column(nullable = false)
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(length = 31, nullable = false)
    val role: MemberRole,

    @Enumerated(EnumType.STRING)
    @Column(length = 31, nullable = false)
    val status: MemberStatus,

    @Column(nullable = false)
    val level: Int,
) {

    fun toDomain(): Member = Member(
        userid = userid,
        nickname = nickname,
        password = password,
        role = role,
        status = status,
        level = level,
    )
}