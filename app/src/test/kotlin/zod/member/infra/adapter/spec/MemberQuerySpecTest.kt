package zod.member.infra.adapter.spec

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import zod.member.application.query.model.AuthUser
import zod.member.infra.entity.QMemberEntity

class MemberQuerySpecTest {

    @Test
    fun `AuthUser 프로젝션은 타입을 유지한다`() {
        val member = QMemberEntity.memberEntity

        val expression = MemberQuerySpec.Select.authUser(member)

        assertEquals(AuthUser::class.java, expression.type)
    }
}
