package zod.member.application.port

import zod.member.application.dto.MemberDto

interface MemberQueryPort {

    fun findLoginUserByUserid(userid: String): MemberDto.LoginUser?

}
