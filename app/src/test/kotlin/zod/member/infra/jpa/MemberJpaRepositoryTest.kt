package zod.member.infra.jpa

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import zod.member.infra.entity.MemberEntity

class MemberJpaRepositoryTest {

    @Test
    fun `MemberJpaRepository는 userid 조회 메서드를 가진다`() {
        val method = MemberJpaRepository::class.java.methods
            .firstOrNull { it.name == "findByUserid" }

        assertNotNull(method)
        assertEquals(MemberEntity::class.java, method?.returnType)
    }
}
