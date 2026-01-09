package zod.member.infra.adapter

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import zod.member.application.port.MemberQueryPort
import zod.member.application.query.model.AuthUser
import zod.member.infra.adapter.spec.MemberQuerySpec
import zod.member.infra.entity.QMemberEntity

@Repository
class MemberQueryAdapter(
    private val queryFactory: JPAQueryFactory
) : MemberQueryPort {

    override fun findAuthUserByUserid(userid: String): AuthUser? {
        val member = QMemberEntity.memberEntity

        return queryFactory
            .select(
                MemberQuerySpec.Select.authUser(member)
            )
            .from(member)
            .where(member.userid.eq(userid))
            .fetchFirst()
    }
}
