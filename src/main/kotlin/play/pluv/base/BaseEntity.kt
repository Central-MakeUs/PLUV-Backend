package play.pluv.base

import jakarta.persistence.Column
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
abstract class BaseEntity {

    @JvmField
    @Column(updatable = false, nullable = false)
    @CreatedDate
    var createdAt: LocalDateTime? = null

    @JvmField
    @Column(nullable = false)
    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    fun getCreatedAt(): LocalDateTime? = createdAt
}