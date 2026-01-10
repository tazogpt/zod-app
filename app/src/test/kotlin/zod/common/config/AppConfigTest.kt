package zod.common.config

import jakarta.persistence.EntityManager
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotNull
import org.junit.jupiter.api.Test
import org.mockito.Mockito
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import java.util.concurrent.ScheduledThreadPoolExecutor

class AppConfigTest {

    @Test
    fun `스케줄러와 실행기는 기본 풀 사이즈를 가진다`() {
        val config = AppConfig(Mockito.mock(EntityManager::class.java))

        val scheduler = config.taskScheduler() as ThreadPoolTaskScheduler
        val executor = config.taskExecutor() as ThreadPoolTaskExecutor
        val paymentExecutor = config.paymentExecutor()

        val scheduledExecutor = requireNotNull(scheduler.scheduledExecutor)
        val threadPoolExecutor = requireNotNull(scheduledExecutor as? ScheduledThreadPoolExecutor)
        assertEquals(30, threadPoolExecutor.corePoolSize)
        assertEquals(30, executor.corePoolSize)
        assertEquals(1, paymentExecutor.corePoolSize)
        assertEquals(1, paymentExecutor.maxPoolSize)
    }

    @Test
    fun `ObjectMapper와 QueryFactory 빈을 생성한다`() {
        val config = AppConfig(Mockito.mock(EntityManager::class.java))

        val mapper = config.objectMapper()
        val queryFactory = config.queryFactory()

        assertNotNull(mapper)
        assertNotNull(queryFactory)
    }
}
