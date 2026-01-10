package zod.config.infra.adapter

import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Repository
import zod.config.application.port.ConfigQueryPort
import zod.config.domain.ConfigEntry
import zod.config.domain.ConfigKey
import zod.config.infra.cache.ConfigCache
import zod.config.infra.jpa.JsonConfigJpaRepository

@Repository
class ConfigQueryAdapter(
    private val jsonConfigJpaRepository: JsonConfigJpaRepository,
    private val configCache: ConfigCache,
) : ConfigQueryPort {

    override fun findByKey(key: ConfigKey): ConfigEntry? {
        val cached = configCache.get(key.value)
        if (cached != null) {
            return cached
        }

        val entity = jsonConfigJpaRepository.findByIdOrNull(key.value) ?: return null
        val entry = ConfigEntry(ConfigKey.of(entity.code), entity.json)
        configCache.put(entry)
        return entry
    }
}
