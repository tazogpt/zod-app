package zod.member.infra.adapter

import com.querydsl.core.types.ConstructorExpression
import com.querydsl.core.types.Predicate
import com.querydsl.jpa.impl.JPAQuery
import com.querydsl.jpa.impl.JPAQueryFactory
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import zod.member.application.query.model.AuthUser
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus
import zod.member.infra.entity.QMemberEntity

class MemberQueryAdapterTest {

    @Test
    fun `userid로 조회한 사용자를 반환한다`() {
        val queryFactory = Mockito.mock(JPAQueryFactory::class.java)
        @Suppress("UNCHECKED_CAST")
        val query = Mockito.mock(JPAQuery::class.java) as JPAQuery<AuthUser>
        val adapter = MemberQueryAdapter(queryFactory)
        val expected = AuthUser(
            userid = "user-1",
            nickname = "nick",
            password = "pw",
            role = MemberRole.USER,
            status = MemberStatus.ACTIVE,
            level = 1,
        )

        Mockito.`when`(
            queryFactory.select(ArgumentMatchers.any(ConstructorExpression::class.java))
        ).thenReturn(query)
        Mockito.`when`(query.from(ArgumentMatchers.any(QMemberEntity::class.java))).thenReturn(query)
        Mockito.`when`(query.where(ArgumentMatchers.any(Predicate::class.java))).thenReturn(query)
        Mockito.`when`(query.fetchFirst()).thenReturn(expected)

        val result = adapter.findAuthUserByUserid("user-1")

        assertEquals(expected, result)
    }
}
