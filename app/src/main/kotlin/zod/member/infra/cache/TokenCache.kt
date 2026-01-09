package zod.member.infra.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import java.time.Duration

@Component
class TokenCache(
    @Value("\${security.jwt.refresh-mins}") private val refreshMinutes: Long,
) {
    private val cache: Cache<String, String> = Caffeine.newBuilder()
        .expireAfterWrite(Duration.ofMinutes(refreshMinutes))
        .maximumSize(10_000)
        .build()

    fun put(userid: String, refreshToken: String) {
        cache.put(userid, refreshToken)
    }

    fun get(userid: String): String? {
        return cache.getIfPresent(userid)
    }

    fun evict(userid: String) {
        cache.invalidate(userid)
    }
}
