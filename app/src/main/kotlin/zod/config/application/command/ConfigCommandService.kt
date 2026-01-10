package zod.config.application.command

import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import zod.config.application.dto.ConfigDto
import zod.config.application.port.ConfigCommandPort
import zod.config.domain.ConfigEntry
import zod.config.domain.ConfigKey

@Service
class ConfigCommandService(
    private val configCommandPort: ConfigCommandPort,
) {

    @Transactional
    fun update(command: ConfigDto.UpdateCommand): ConfigDto.ConfigResponse {
        val key = ConfigKey.of(command.key)
        val entry = ConfigEntry(key, command.json)
        configCommandPort.save(entry)
        return ConfigDto.ConfigResponse(key.value, entry.json)
    }
}
