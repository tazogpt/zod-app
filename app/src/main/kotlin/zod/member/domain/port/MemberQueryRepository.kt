package zod.member.domain.port

import zod.member.domain.model.Member

interface MemberQueryRepository {
    fun findByUserid(userid: String): Member?
}
