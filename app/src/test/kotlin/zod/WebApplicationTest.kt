package zod

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.springframework.boot.autoconfigure.SpringBootApplication

class WebApplicationTest {

    @Test
    fun `WebApplication은 SpringBootApplication 애너테이션을 가진다`() {
        val hasAnnotation = WebApplication::class.java
            .isAnnotationPresent(SpringBootApplication::class.java)

        assertTrue(hasAnnotation)
    }
}
