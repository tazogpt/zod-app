package zod.config.infra.cache

import com.github.benmanes.caffeine.cache.Cache
import com.github.benmanes.caffeine.cache.Caffeine
import org.springframework.stereotype.Component
import zod.config.domain.ConfigEntry

@Component
class ConfigCache {
    private val cache: Cache<String, ConfigEntry> = Caffeine.newBuilder()
        .maximumSize(10_000)
        .build()

    fun get(key: String): ConfigEntry? = cache.getIfPresent(key)

    fun put(entry: ConfigEntry) {
        cache.put(entry.key.value, entry)
    }

    fun evict(key: String) {
        cache.invalidate(key)
    }
}
