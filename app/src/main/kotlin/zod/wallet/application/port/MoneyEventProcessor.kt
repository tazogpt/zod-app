package zod.wallet.application.port

import zod.wallet.domain.model.MoneyEvent

interface MoneyEventProcessor {
    fun process(event: MoneyEvent)
}
