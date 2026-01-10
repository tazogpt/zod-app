package zod.member.infra.jpa

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import zod.member.infra.entity.TokenEntity

class TokenJpaRepositoryTest {

    @Test
    fun `TokenJpaRepository는 userid 조회 메서드를 가진다`() {
        val method = TokenJpaRepository::class.java.methods
            .firstOrNull { it.name == "findByUserid" }

        assertNotNull(method)
        assertEquals(TokenEntity::class.java, method?.returnType)
    }
}
