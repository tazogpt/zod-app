package zod.config.application.port

import zod.config.domain.ConfigEntry
import zod.config.domain.ConfigKey

interface ConfigQueryPort {
    fun findByKey(key: ConfigKey): ConfigEntry?
}
