package zod.config.application.port

import zod.config.domain.ConfigEntry

interface ConfigCommandPort {
    fun save(entry: ConfigEntry)
}
