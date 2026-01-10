package zod.common.config

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

class WebConfigTest {

    @Test
    fun `WebConfig는 WebMvcConfigurer를 구현한다`() {
        val config = WebConfig()

        assertTrue(config is WebMvcConfigurer)
    }
}
