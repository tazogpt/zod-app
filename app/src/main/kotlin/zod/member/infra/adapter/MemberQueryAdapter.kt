package zod.member.infra.adapter

import org.springframework.stereotype.Repository
import zod.member.domain.model.Member
import zod.member.domain.port.MemberQueryRepository
import zod.member.infra.jpa.MemberJpaRepository

@Repository
class MemberQueryAdapter(
    private val memberJpaRepository: MemberJpaRepository,
) : MemberQueryRepository {

    override fun findByUserid(userid: String): Member? {
        return memberJpaRepository.findByUserid(userid)?.toDomain()
    }
}
