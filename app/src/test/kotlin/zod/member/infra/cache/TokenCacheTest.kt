package zod.member.infra.cache

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test

class TokenCacheTest {

    @Test
    fun `캐시에 저장한 토큰을 조회한다`() {
        val cache = TokenCache(5)

        cache.put("user-1", "refresh")

        assertEquals("refresh", cache.get("user-1"))
    }

    @Test
    fun `캐시에서 토큰을 삭제한다`() {
        val cache = TokenCache(5)
        cache.put("user-1", "refresh")

        cache.evict("user-1")

        assertNull(cache.get("user-1"))
    }
}
