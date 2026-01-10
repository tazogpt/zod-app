package zod.member.application.port

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class TokenCommandPortTest {

    @Test
    fun `TokenCommandPort는 저장과 삭제 메서드를 가진다`() {
        val saveMethod = TokenCommandPort::class.java.methods
            .firstOrNull { it.name == "save" }
        val deleteMethod = TokenCommandPort::class.java.methods
            .firstOrNull { it.name == "deleteByUserid" }

        assertNotNull(saveMethod)
        assertNotNull(deleteMethod)
        assertEquals(3, saveMethod?.parameterCount)
        assertEquals(1, deleteMethod?.parameterCount)
    }
}
