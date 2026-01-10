package zod.config.infra.adapter

import org.springframework.stereotype.Repository
import zod.config.application.port.ConfigCommandPort
import zod.config.domain.ConfigEntry
import zod.config.infra.cache.ConfigCache
import zod.config.infra.entity.JsonConfigEntity
import zod.config.infra.jpa.JsonConfigJpaRepository

@Repository
class ConfigCommandAdapter(
    private val jsonConfigJpaRepository: JsonConfigJpaRepository,
    private val configCache: ConfigCache,
) : ConfigCommandPort {

    override fun save(entry: ConfigEntry) {
        jsonConfigJpaRepository.save(JsonConfigEntity(entry.key.value, entry.json))
        configCache.put(entry)
    }
}
