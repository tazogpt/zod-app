package zod.member.domain.enums

enum class MemberRole(
    val label: String,
    val group: Group
) {
    GOD("시스템운영자", Group.ADMIN),
    SUPER("슈퍼관리자", Group.ADMIN),
    ADMIN("관리자", Group.ADMIN),

    AGENCY1("총본사", Group.PARTNER),
    AGENCY2("본사", Group.PARTNER),
    AGENCY3("총판", Group.PARTNER),
    AGENCY4("대리점", Group.PARTNER),
    AGENCY5("지점", Group.PARTNER),
    AGENCY6("매장", Group.PARTNER),

    USER("회원", Group.USER),
    DUMMY("NPC", Group.USER),
    NONE("에러", Group.USER);
    ;

    enum class Group {
        ADMIN,
        PARTNER,
        USER
    }
}
