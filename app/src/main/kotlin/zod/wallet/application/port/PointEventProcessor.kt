package zod.wallet.application.port

import zod.wallet.domain.model.PointEvent

interface PointEventProcessor {
    fun process(event: PointEvent)
}
