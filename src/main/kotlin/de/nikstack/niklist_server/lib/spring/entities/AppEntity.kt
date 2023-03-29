package de.nikstack.niklist_server.lib.spring.entities

import jakarta.persistence.*
import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime
import java.util.*


@Entity
@EntityListeners(AuditingEntityListener::class)
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
abstract class AppEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    open val id: UUID? = null

    @CreatedDate
    open var createdDate: LocalDateTime? = null
        protected set

    @LastModifiedDate
    open var lastModifiedDate: LocalDateTime? = null
        protected set

    @CreatedBy
    open var createdBy: String? = null
        protected set

    @LastModifiedBy
    open var lastModifiedBy: String? = null
        protected set
}
