package zod.member.infra.adapter

import org.springframework.stereotype.Repository
import zod.member.application.port.SignupCommandPort
import zod.member.domain.model.SignupRequest
import zod.member.domain.enums.MemberRole
import zod.member.domain.enums.MemberStatus
import zod.member.infra.entity.MemberEntity
import zod.member.infra.jpa.MemberJpaRepository

@Repository
class SignupCommandAdapter(
    private val memberJpaRepository: MemberJpaRepository,
) : SignupCommandPort {

    override fun save(request: SignupRequest) {
        memberJpaRepository.save(
            MemberEntity(
                userid = request.userid,
                nickname = request.nickname,
                password = request.password,
                role = MemberRole.USER,
                status = MemberStatus.SIGNUP,
                level = 1,
                signupAt = request.requestedAt,
            ),
        )
    }
}
