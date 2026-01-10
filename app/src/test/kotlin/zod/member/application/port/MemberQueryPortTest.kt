package zod.member.application.port

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import zod.member.application.query.model.AuthUser

class MemberQueryPortTest {

    @Test
    fun `MemberQueryPort는 사용자 조회 메서드를 가진다`() {
        val method = MemberQueryPort::class.java.methods
            .firstOrNull { it.name == "findAuthUserByUserid" }

        assertNotNull(method)
        assertEquals(AuthUser::class.java, method?.returnType)
    }
}
