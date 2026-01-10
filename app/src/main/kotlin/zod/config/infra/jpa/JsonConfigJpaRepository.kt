package zod.config.infra.jpa

import org.springframework.data.jpa.repository.JpaRepository
import zod.config.infra.entity.JsonConfigEntity

interface JsonConfigJpaRepository : JpaRepository<JsonConfigEntity, String>
