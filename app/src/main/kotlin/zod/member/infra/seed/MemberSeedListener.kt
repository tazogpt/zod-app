package zod.member.infra.seed

import org.springframework.boot.context.event.ApplicationReadyEvent
import org.springframework.context.event.EventListener
import org.springframework.scheduling.annotation.Async
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus
import zod.member.infra.entity.MemberEntity
import zod.member.infra.jpa.MemberJpaRepository

@Component
class MemberSeedListener(
    private val memberJpaRepository: MemberJpaRepository,
    private val passwordEncoder: PasswordEncoder,
) {

    @Async
    @Transactional
    @EventListener(ApplicationReadyEvent::class)
    fun seedMembers() {
        val seeds = listOf(
            SeedMember("admin", "관리자", "1111", MemberRole.GOD),
            SeedMember("agency", "총본사", "1111", MemberRole.AGENCY1),
            SeedMember("test", "테스트", "1111", MemberRole.USER, 1),
        )

        seeds.forEach { seed ->
            val existsByUserid = memberJpaRepository.existsByUserid(seed.userid)
            val existsByNickname = memberJpaRepository.existsByNickname(seed.nickname)
            if (existsByUserid || existsByNickname) {
                return@forEach
            }

            memberJpaRepository.save(
                MemberEntity(
                    userid = seed.userid,
                    nickname = seed.nickname,
                    password = requireNotNull(passwordEncoder.encode(seed.password)),
                    role = seed.role,
                    status = MemberStatus.ACTIVE,
                    level = seed.level,
                )
            )
        }
    }

    private data class SeedMember(
        val userid: String,
        val nickname: String,
        val password: String,
        val role: MemberRole,
        val level: Int = 0
    )
}
