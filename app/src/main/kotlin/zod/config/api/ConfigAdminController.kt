package zod.config.api

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import zod.common.response.ApiResponse
import zod.config.application.command.ConfigCommandService
import zod.config.application.dto.ConfigDto
import zod.config.application.query.ConfigQueryService

@RestController
@RequestMapping("/api/admin/config")
class ConfigAdminController(
    private val configQueryService: ConfigQueryService,
    private val configCommandService: ConfigCommandService,
) {

    @GetMapping("/{key}")
    fun getConfig(@PathVariable key: String): ResponseEntity<out ApiResponse<out ConfigDto.ConfigResponse>> {
        if (key.isBlank()) {
            return ApiResponse.error("INVALID_KEY", "설정 키가 비어 있습니다.")
                .toResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val result = configQueryService.findByKey(key)
            ?: return ApiResponse.error("NOT_FOUND", "설정을 찾을 수 없습니다.")
                .toResponseEntity(HttpStatus.NOT_FOUND)

        return ApiResponse.success(result).toResponseEntity()
    }

    @PutMapping("/{key}")
    fun updateConfig(
        @PathVariable key: String,
        @RequestBody request: ConfigDto.UpdateRequest,
    ): ResponseEntity<out ApiResponse<out ConfigDto.ConfigResponse>> {
        if (key.isBlank()) {
            return ApiResponse.error("INVALID_KEY", "설정 키가 비어 있습니다.")
                .toResponseEntity(HttpStatus.BAD_REQUEST)
        }

        val result = configCommandService.update(ConfigDto.UpdateCommand(key, request.json))
        return ApiResponse.success(result).toResponseEntity()
    }
}
