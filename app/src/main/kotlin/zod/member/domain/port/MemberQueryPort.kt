package zod.member.domain.port

import zod.member.application.dto.MemberDto

interface MemberQueryPort {

    fun findLoginUserByUserid(userid: String): MemberDto.LoginUser?

}
