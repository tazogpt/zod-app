package zod.config.domain

@JvmInline
value class ConfigKey(val value: String) {
    companion object {
        fun of(raw: String): ConfigKey = ConfigKey(raw.trim())
    }
}
