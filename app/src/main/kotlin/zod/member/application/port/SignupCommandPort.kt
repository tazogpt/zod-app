package zod.member.application.port

import zod.member.domain.model.SignupRequest

interface SignupCommandPort {
    fun save(request: SignupRequest)
}
