package zod.member.infra.adapter

import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository
import zod.member.application.dto.MemberDto
import zod.member.domain.port.MemberQueryPort
import zod.member.infra.adapter.spec.MemberQuerySpec
import zod.member.infra.entity.QMemberEntity
import zod.member.infra.jpa.MemberJpaRepository

@Repository
class MemberQueryAdapter(
    private val queryFactory: JPAQueryFactory,
    private val memberJpaRepository: MemberJpaRepository,
) : MemberQueryPort {

    override fun findLoginUserByUserid(userid: String): MemberDto.LoginUser? {
        val member = QMemberEntity.memberEntity

        return queryFactory
            .select(
                MemberQuerySpec.Select.loginUser(member)
            )
            .from(member)
            .where(member.userid.eq(userid))
            .fetchFirst()
    }
}
