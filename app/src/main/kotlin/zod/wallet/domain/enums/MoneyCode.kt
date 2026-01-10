package zod.wallet.domain.enums

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class MoneyCode(
    val label: String,
    val group: Group,
) {
    PLUS("관리자 증액", Group.PLUS_MINUS),
    MINUS("관리자 차감", Group.PLUS_MINUS),

    DEPOSIT("입금", Group.BANKING),
    DEPOSIT_CANCEL("입금취소", Group.BANKING),

    WITHDRAW("출금", Group.BANKING),
    WITHDRAW_CANCEL("출금취소", Group.BANKING),

    CASINO_BET("카지노베팅", Group.CASINO),
    CASINO_HIT("카지노적중", Group.CASINO),
    CASINO_CANCEL("카지노베팅 취소", Group.CASINO),
    CASINO_EXCEED_CREDIT("카지노 최대 당첨금액 제한", Group.CASINO),

    SPORTS_BET("스포츠베팅", Group.SPORTS),
    SPORTS_HIT("스포츠적중", Group.SPORTS),
    SPORTS_CANCEL("스포츠베팅 취소", Group.SPORTS),
    SPORTS_ROLLBACK("스포츠적중 롤백", Group.SPORTS),

    ZONE_BET("실시간베팅", Group.ZONE),
    ZONE_HIT("실시간적중", Group.ZONE),
    ZONE_CANCEL("실시간베팅 취소", Group.ZONE),
    ZONE_ROLLBACK("실시간적중 롤백", Group.ZONE),

    INPLAY_BET("인플레이베팅", Group.INPLAY),
    INPLAY_HIT("인플레이적중", Group.INPLAY),
    INPLAY_CANCEL("인플레이베팅 취소", Group.INPLAY),
    INPLAY_ROLLBACK("인플레이적중 롤백", Group.INPLAY),

    BINARY_BET("바이너리베팅", Group.BINARY),
    BINARY_HIT("바이너리적중", Group.BINARY),
    BINARY_CANCEL("바이너리베팅 취소", Group.BINARY),
    BINARY_ROLLBACK("바이너리적중 롤백", Group.BINARY),

    EXCHANGE_POINT("포인트 전환", Group.EXCHANGE),
    EXCHANGE_COMP("콤프 전환", Group.EXCHANGE),
    EXCHANGE_LOSING("루징 전환", Group.EXCHANGE),

    MONEY_SEND("머니 보냄", Group.SEND_RECEIVE),
    MONEY_RECEIVE("머니 받음", Group.SEND_RECEIVE),
    ;

    fun rollback(): MoneyCode {
        return when (this) {
            DEPOSIT -> DEPOSIT_CANCEL
            WITHDRAW -> WITHDRAW_CANCEL
            ZONE_HIT -> ZONE_ROLLBACK
            SPORTS_BET -> SPORTS_CANCEL
            ZONE_BET -> ZONE_CANCEL
            INPLAY_BET -> INPLAY_CANCEL
            INPLAY_HIT -> INPLAY_ROLLBACK
            else -> throw IllegalArgumentException("롤백코드를 반환할 수 없습니다. - $this")
        }
    }

    @JsonValue
    fun toValue(): String = name.lowercase()

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): MoneyCode {
            return MoneyCode.entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown value: $value")
        }
    }

    enum class Group(
        val label: String,
        val display: Boolean,
    ) {
        BANKING("입출금", true),
        SPORTS("스포츠", true),
        ZONE("실시간", true),
        INPLAY("인플레이", true),
        CASINO("카지노", true),
        BINARY("바이너리", false),
        PLUS_MINUS("관리자 증차감", true),
        EXCHANGE("전환", true),
        SEND_RECEIVE("송금", true),
        ;

        fun getCodes(): List<MoneyCode> = MoneyCode.entries
            .filter { it.group == this }

        @JsonValue
        fun toValue(): String = name.lowercase()

        companion object {
            @JsonCreator
            @JvmStatic
            fun fromValue(value: String): Group {
                return Group.entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                    ?: throw IllegalArgumentException("Unknown value: $value")
            }
        }
    }
}
