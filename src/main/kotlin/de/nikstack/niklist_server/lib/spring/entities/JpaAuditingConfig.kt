package de.nikstack.niklist_server.lib.spring.entities

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.util.*

@Configuration
@EnableJpaAuditing
class JpaAuditingConfig {
    @Bean
    fun auditorProvider() = AuditorAware {
        Optional.of(SecurityContextHolder.getContext().authentication.name)
    }
}
