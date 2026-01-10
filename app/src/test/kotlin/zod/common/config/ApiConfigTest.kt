package zod.common.config

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test

class ApiConfigTest {

    @Test
    fun `OpenAPI 설정은 기본 정보를 포함한다`() {
        val config = ApiConfig()

        val openApi = config.openAPI()

        val info = openApi.info
        assertNotNull(info)
        assertEquals("Zod API", info.title)
        assertEquals("Zod backend API", info.description)
        assertEquals("v1", info.version)
    }
}
