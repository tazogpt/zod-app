package zod.wallet.domain.model

data class Wallet(
    val userid: String,
    val money: Money,
    val point: Point,
)
