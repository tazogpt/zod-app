package zod.member.infra.adapter

import org.junit.jupiter.api.Test
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import zod.member.infra.cache.TokenCache
import zod.member.infra.entity.TokenEntity
import zod.member.infra.jpa.TokenJpaRepository

class TokenCommandAdapterTest {

    @Test
    fun `저장은 저장소와 캐시에 반영한다`() {
        val repository = Mockito.mock(TokenJpaRepository::class.java)
        val cache = Mockito.mock(TokenCache::class.java)
        val adapter = TokenCommandAdapter(repository, cache)

        adapter.save("user-1", "access", "refresh")

        Mockito.verify(repository).save(
            ArgumentMatchers.argThat { entity: TokenEntity ->
                entity.userid == "user-1" &&
                    entity.accessToken == "access" &&
                    entity.refreshToken == "refresh"
            }
        )
        Mockito.verify(cache).put("user-1", "refresh")
    }

    @Test
    fun `삭제는 저장소와 캐시에서 제거한다`() {
        val repository = Mockito.mock(TokenJpaRepository::class.java)
        val cache = Mockito.mock(TokenCache::class.java)
        val adapter = TokenCommandAdapter(repository, cache)

        adapter.deleteByUserid("user-1")

        Mockito.verify(repository).deleteById("user-1")
        Mockito.verify(cache).evict("user-1")
    }
}
