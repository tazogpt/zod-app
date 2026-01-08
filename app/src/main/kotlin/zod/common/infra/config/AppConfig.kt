package zod.common.infra.config

import jakarta.persistence.EntityManager
import jakarta.persistence.PersistenceContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary
import org.springframework.scheduling.TaskScheduler
import org.springframework.scheduling.annotation.EnableAsync
import org.springframework.scheduling.annotation.EnableScheduling
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler
import org.springframework.transaction.annotation.EnableTransactionManagement

@Configuration
@EnableTransactionManagement
@EnableAsync
@EnableScheduling
class AppConfig(
    @PersistenceContext
    private val entityManager: EntityManager,
) {

    @Bean
    fun taskScheduler(): TaskScheduler {
        return ThreadPoolTaskScheduler().apply {
            poolSize = 30
            initialize()
        }
    }

    @Bean
    @Primary
    fun taskExecutor(): ThreadPoolTaskExecutor {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 30
            initialize()
        }
    }

    @Bean("paymentExecutor")
    fun paymentExecutor(): ThreadPoolTaskExecutor {
        return ThreadPoolTaskExecutor().apply {
            corePoolSize = 1
            maxPoolSize = 1
            initialize()
        }
    }
}
