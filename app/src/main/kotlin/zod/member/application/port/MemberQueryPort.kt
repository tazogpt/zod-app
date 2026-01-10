package zod.member.application.port

import zod.member.application.query.model.AuthUser

interface MemberQueryPort {

    fun findAuthUserByUserid(userid: String): AuthUser?
    fun existsByUserid(userid: String): Boolean
    fun existsByNickname(nickname: String): Boolean

}
