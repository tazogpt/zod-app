package zod.member.infra.adapter

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import zod.member.infra.cache.TokenCache
import zod.member.infra.entity.TokenEntity
import zod.member.infra.jpa.TokenJpaRepository
import java.time.LocalDateTime

class TokenQueryAdapterTest {

    @Test
    fun `캐시에 값이 있으면 저장소를 호출하지 않는다`() {
        val repository = Mockito.mock(TokenJpaRepository::class.java)
        val cache = TokenCache(5)
        val adapter = TokenQueryAdapter(repository, cache)
        cache.put("user-1", "cached")

        val result = adapter.findRefreshTokenByUserid("user-1")

        assertEquals("cached", result)
        Mockito.verifyNoInteractions(repository)
    }

    @Test
    fun `캐시에 없으면 저장소에서 조회하고 캐시에 저장한다`() {
        val repository = Mockito.mock(TokenJpaRepository::class.java)
        val cache = TokenCache(5)
        val adapter = TokenQueryAdapter(repository, cache)
        val entity = TokenEntity(
            userid = "user-1",
            accessToken = "access",
            refreshToken = "refresh",
            updatedAt = LocalDateTime.now(),
        )
        Mockito.`when`(repository.findByUserid("user-1")).thenReturn(entity)

        val result = adapter.findRefreshTokenByUserid("user-1")

        assertEquals("refresh", result)
        assertEquals("refresh", cache.get("user-1"))
    }

    @Test
    fun `저장소에도 없으면 null을 반환한다`() {
        val repository = Mockito.mock(TokenJpaRepository::class.java)
        val cache = TokenCache(5)
        val adapter = TokenQueryAdapter(repository, cache)
        Mockito.`when`(repository.findByUserid("user-1")).thenReturn(null)

        val result = adapter.findRefreshTokenByUserid("user-1")

        assertNull(result)
    }
}
