package zod.common.annotation

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import kotlin.annotation.AnnotationRetention
import kotlin.annotation.AnnotationTarget
import kotlin.annotation.Retention
import kotlin.annotation.Target

class AnnotationTest {

    @Test
    fun `AllOpen은 클래스 대상과 런타임 보존을 가진다`() {
        val target = AllOpen::class.annotations.filterIsInstance<Target>().first()
        val retention = AllOpen::class.annotations.filterIsInstance<Retention>().first()

        assertTrue(target.allowedTargets.contains(AnnotationTarget.CLASS))
        assertEquals(AnnotationRetention.RUNTIME, retention.value)
    }

    @Test
    fun `NoArgs는 클래스 대상과 런타임 보존을 가진다`() {
        val target = NoArgs::class.annotations.filterIsInstance<Target>().first()
        val retention = NoArgs::class.annotations.filterIsInstance<Retention>().first()

        assertTrue(target.allowedTargets.contains(AnnotationTarget.CLASS))
        assertEquals(AnnotationRetention.RUNTIME, retention.value)
    }
}
