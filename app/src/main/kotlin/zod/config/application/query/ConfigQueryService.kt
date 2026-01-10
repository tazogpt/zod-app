package zod.config.application.query

import org.springframework.stereotype.Service
import zod.config.application.dto.ConfigDto
import zod.config.application.port.ConfigQueryPort
import zod.config.domain.ConfigKey

@Service
class ConfigQueryService(
    private val configQueryPort: ConfigQueryPort,
) {
    fun findByKey(key: String): ConfigDto.ConfigResponse? {
        val entry = configQueryPort.findByKey(ConfigKey.of(key)) ?: return null
        return ConfigDto.ConfigResponse(entry.key.value, entry.json)
    }
}
