package zod.member.infra.adapter.spec

import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Projections
import zod.member.application.query.model.AuthUser
import zod.member.infra.entity.QMemberEntity

object MemberQuerySpec {

    object Select {
        fun authUser(member: QMemberEntity): ConstructorExpression<AuthUser> {
            return Projections.constructor(
                AuthUser::class.java,
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
