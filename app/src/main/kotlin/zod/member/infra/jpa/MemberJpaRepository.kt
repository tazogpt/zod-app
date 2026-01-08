package zod.member.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import zod.member.infra.entity.MemberEntity

interface MemberJpaRepository : JpaRepository<MemberEntity, Long> {
    fun findByUserid(userid: String): MemberEntity?
}
