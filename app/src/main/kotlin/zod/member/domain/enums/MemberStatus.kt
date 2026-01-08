package zod.member.domain.enums

enum class MemberStatus(
    val label: String
) {
    SIGNUP("가입신청"),
    REVIEW("가입심사"),
    REFUSE("가입거절"),
    ACTIVE("정상"),
    BLOCK("로그인금지"),
    SECESSION("탈퇴");
}
