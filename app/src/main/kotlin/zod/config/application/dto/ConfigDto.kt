package zod.config.application.dto

class ConfigDto {

    data class UpdateRequest(
        val json: String,
    )

    data class UpdateCommand(
        val key: String,
        val json: String,
    )

    data class ConfigResponse(
        val key: String,
        val json: String,
    )
}
