package zod.wallet.domain.enums

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonValue

enum class PointCode(
    val label: String,
    val group: Group,
) {
    PLUS("관리자 증액", Group.PLUS_MINUS),
    MINUS("관리자 차감", Group.PLUS_MINUS),

    DEPOSIT("충전 보너스", Group.BANKING),
    DEPOSIT_CANCEL("충전보너스 취소", Group.BANKING),

    STOCK("총판 배당금", Group.STOCK),

    LOSE("미적중 포인트", Group.LOSE),
    LOSE_CANCEL("미적중 포인트 취소", Group.LOSE),

    RECOMM("추천인 미적중 포인트", Group.LOSE),
    RECOMM_CANCEL("추천인 미적중 취소", Group.LOSE),

    RECOMM_DEPOSIT("추천인 입금 포인트", Group.BANKING),
    RECOMM_DEPOSIT_CANCEL("추천인 입금 취소", Group.BANKING),

    JOIN("가입축하 포인트", Group.EVENT),
    LOGIN("첫로그인 포인트", Group.EVENT),
    BOARD("글작성 포인트", Group.EVENT),
    COMMENT("댓글 포인트", Group.EVENT),
    EVENT("이벤트 포인트", Group.EVENT),

    EXCHANGE_POINT("포인트 머니전환", Group.EXCHANGE),

    ROLL_SPORTS("스포츠롤링", Group.ROLL),
    ROLL_SPORTS_ROLLBACK("스포츠롤링 취소", Group.ROLL),

    ROLL_INPLAY("인플레이롤링", Group.ROLL),
    ROLL_INPLAY_ROLLBACK("인플레이롤링 취소", Group.ROLL),

    ROLL_ZONE("실시간롤링", Group.ROLL),
    ROLL_ZONE_ROLLBACK("실시간롤링 취소", Group.ROLL),

    ROLL_CASINO("카지노롤링", Group.ROLL),
    ROLL_SLOT("슬롯롤링", Group.ROLL),
    ROLL_CASINO_ROLLBACK("카지노롤링 취소", Group.ROLL),
    ROLL_SLOT_ROLLBACK("슬롯롤링 취소", Group.ROLL),

    ROLL_CASINO_RECOMM("카지노 추천인롤링", Group.ROLL),
    ROLL_CASINO_RECOMM_ROLLBACK("카지노 추천인롤링 취소", Group.ROLL),
    ROLL_SLOT_RECOMM("슬롯 추천인롤링", Group.ROLL),
    ROLL_SLOT_RECOMM_ROLLBACK("슬롯 추천인롤링 취소", Group.ROLL),

    POINT_SEND("포인트 보냄", Group.SEND_RECEIVE),
    POINT_RECEIVE("포인트 받음", Group.SEND_RECEIVE),
    ;

    fun rollback(): PointCode {
        return when (this) {
            DEPOSIT -> DEPOSIT_CANCEL
            LOSE -> LOSE_CANCEL
            ROLL_SPORTS -> ROLL_SPORTS_ROLLBACK
            ROLL_INPLAY -> ROLL_INPLAY_ROLLBACK
            ROLL_ZONE -> ROLL_ZONE_ROLLBACK
            ROLL_CASINO -> ROLL_CASINO_ROLLBACK
            ROLL_SLOT -> ROLL_SLOT_ROLLBACK
            ROLL_CASINO_RECOMM -> ROLL_CASINO_RECOMM_ROLLBACK
            ROLL_SLOT_RECOMM -> ROLL_SLOT_RECOMM_ROLLBACK
            else -> throw IllegalArgumentException("롤백코드를 반환할 수 없습니다. - $this")
        }
    }

    @JsonValue
    fun toValue(): String = name.lowercase()

    companion object {
        @JsonCreator
        @JvmStatic
        fun fromValue(value: String): PointCode {
            return PointCode.entries.firstOrNull { it.name.equals(value, ignoreCase = true) }
                ?: throw IllegalArgumentException("Unknown value: $value")
        }
    }

    enum class Group(
        val label: String,
        val display: Boolean,
    ) {
        BANKING("입급 보너스", true),
        STOCK("죽장", true),
        LOSE("미적중 포인트", true),
        EVENT("이벤트", true),
        ROLL("롤링", true),
        PLUS_MINUS("관리자 증차감", true),
        EXCHANGE("머니전환", true),
        SEND_RECEIVE("송금", true),
        ;

        fun getCodes(): List<PointCode> = PointCode.entries
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
