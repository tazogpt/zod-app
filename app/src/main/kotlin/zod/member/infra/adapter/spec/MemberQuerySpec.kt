package zod.member.infra.adapter.spec

import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import zod.member.application.dto.MemberDto
import zod.member.infra.entity.QMemberEntity

object MemberQuerySpec {

    object Select {
        fun loginUser(member: QMemberEntity): ConstructorExpression<MemberDto.LoginUser> {
            return Projections.constructor(
                MemberDto.LoginUser::class.java,
                member.userid,
                member.nickname,
                member.password,
                member.role,
                member.status,
                member.level
            )
        }
    }

    object Where {

    }

    object OrderBy {

    }
}