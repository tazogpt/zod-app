package zod.member.domain.enums

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class MemberRoleTest {

    @Test
    fun `USER 역할은 회원 그룹에 속한다`() {
        assertEquals(MemberRole.Group.USER, MemberRole.USER.group)
        assertEquals("회원", MemberRole.USER.label)
    }

    @Test
    fun `ADMIN 역할은 관리자 그룹에 속한다`() {
        assertEquals(MemberRole.Group.ADMIN, MemberRole.ADMIN.group)
        assertEquals("관리자", MemberRole.ADMIN.label)
    }
}
