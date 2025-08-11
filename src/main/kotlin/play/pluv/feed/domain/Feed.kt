package play.pluv.feed.domain

import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import play.pluv.base.BaseEntity
import java.time.LocalDateTime

@Entity
class Feed(
    memberId: Long,
    title: String,
    creatorName: String,
    artistNames: String,
    thumbNailUrl: String,
    songCount: Int,
) : BaseEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @get:JvmName("getNullableId")
    var id: Long? = null
    var memberId: Long = memberId
        private set
    var title: String = title
        private set
    var creatorName: String = creatorName
        private set
    var artistNames: String = artistNames
        private set
    var thumbNailUrl: String = thumbNailUrl
        private set
    var viewable: Boolean = true
        private set
    var songCount: Int = songCount
        private set

    fun getId(): Long = requireNotNull(id)

    constructor(
        id: Long,
        memberId: Long,
        title: String,
        creatorName: String,
        artistNames: String,
        thumbNailUrl: String,
        songCount: Int
    ) : this(memberId, title, creatorName, artistNames, thumbNailUrl, songCount) {
        this.id = id
        this.createdAt = LocalDateTime.now()
        this.updatedAt = LocalDateTime.now()
    }
}
