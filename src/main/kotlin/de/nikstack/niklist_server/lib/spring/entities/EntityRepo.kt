package de.nikstack.niklist_server.lib.spring.entities

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.repository.NoRepositoryBean
import java.util.*

@NoRepositoryBean
interface EntityRepo<E> : JpaRepository<E, UUID>
